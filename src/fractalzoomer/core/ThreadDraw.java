package fractalzoomer.core;

import fractalzoomer.main.MainWindow;
import fractalzoomer.functions.barnsley.Barnsley1;
import fractalzoomer.functions.barnsley.Barnsley3;
import fractalzoomer.functions.barnsley.Barnsley2;
import fractalzoomer.functions.math.Cot;
import fractalzoomer.functions.math.Cosh;
import fractalzoomer.functions.math.Cos;
import fractalzoomer.functions.math.Coth;
import fractalzoomer.functions.math.Exp;
import fractalzoomer.functions.Fractal;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula10;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula11;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula12;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze_f_g.Formula13;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze_f_g.Formula15;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze_f_g.Formula16;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze_f_g.Formula17;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze_f_g.Formula18;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze_f_g.Formula14;
import fractalzoomer.functions.formulas.kaliset.Formula19;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula2;
import fractalzoomer.functions.formulas.kaliset.Formula20;
import fractalzoomer.functions.formulas.kaliset.Formula21;
import fractalzoomer.functions.formulas.kaliset.Formula22;
import fractalzoomer.functions.formulas.kaliset.Formula24;
import fractalzoomer.functions.formulas.kaliset.Formula25;
import fractalzoomer.functions.formulas.kaliset.Formula26;
import fractalzoomer.functions.formulas.kaliset.Formula23;
import fractalzoomer.functions.formulas.m_like_generalization.Formula1;
import fractalzoomer.functions.formulas.m_like_generalization.Formula28;
import fractalzoomer.functions.formulas.m_like_generalization.Formula29;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula27;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula3;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula4;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula5;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula6;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula7;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula8;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula9;
import fractalzoomer.functions.general.FrothyBasin;
import fractalzoomer.functions.root_finding_methods.halley.HalleySin;
import fractalzoomer.functions.root_finding_methods.halley.Halley4;
import fractalzoomer.functions.root_finding_methods.halley.HalleyPoly;
import fractalzoomer.functions.root_finding_methods.halley.HalleyGeneralized8;
import fractalzoomer.functions.root_finding_methods.householder.HouseholderGeneralized8;
import fractalzoomer.functions.root_finding_methods.halley.HalleyCos;
import fractalzoomer.functions.root_finding_methods.halley.HalleyGeneralized3;
import fractalzoomer.functions.root_finding_methods.halley.Halley3;
import fractalzoomer.functions.root_finding_methods.householder.Householder3;
import fractalzoomer.functions.root_finding_methods.householder.HouseholderSin;
import fractalzoomer.functions.root_finding_methods.householder.HouseholderCos;
import fractalzoomer.functions.root_finding_methods.householder.Householder4;
import fractalzoomer.functions.root_finding_methods.householder.HouseholderPoly;
import fractalzoomer.functions.root_finding_methods.householder.HouseholderGeneralized3;
import fractalzoomer.functions.general.Lambda;
import fractalzoomer.functions.math.Log;
import fractalzoomer.functions.magnet.Magnet1;
import fractalzoomer.functions.magnet.Magnet2;
import fractalzoomer.functions.mandelbrot.MandelbrotFifth;
import fractalzoomer.functions.mandelbrot.MandelbrotNth;
import fractalzoomer.functions.general.Manowar;
import fractalzoomer.functions.mandelbrot.MandelbrotSeventh;
import fractalzoomer.functions.mandelbrot.MandelbrotFourth;
import fractalzoomer.functions.mandelbrot.Mandelbar;
import fractalzoomer.functions.mandelbrot.MandelbrotNinth;
import fractalzoomer.functions.mandelbrot.MandelbrotPoly;
import fractalzoomer.functions.mandelbrot.MandelbrotEighth;
import fractalzoomer.functions.mandelbrot.MandelbrotSixth;
import fractalzoomer.functions.mandelbrot.Mandelbrot;
import fractalzoomer.functions.mandelbrot.MandelbrotTenth;
import fractalzoomer.functions.mandelbrot.MandelbrotCubed;
import fractalzoomer.functions.root_finding_methods.newton.NewtonCos;
import fractalzoomer.functions.root_finding_methods.newton.NewtonGeneralized3;
import fractalzoomer.functions.root_finding_methods.newton.Newton3;
import fractalzoomer.functions.root_finding_methods.newton.NewtonGeneralized8;
import fractalzoomer.functions.root_finding_methods.newton.NewtonPoly;
import fractalzoomer.functions.general.Phoenix;
import fractalzoomer.functions.root_finding_methods.newton.NewtonSin;
import fractalzoomer.functions.root_finding_methods.newton.Newton4;
import fractalzoomer.palettes.PaletteColor;
import fractalzoomer.functions.general.Spider;
import fractalzoomer.functions.root_finding_methods.schroder.Schroder4;
import fractalzoomer.functions.root_finding_methods.schroder.SchroderGeneralized3;
import fractalzoomer.functions.root_finding_methods.schroder.SchroderSin;
import fractalzoomer.functions.root_finding_methods.schroder.Schroder3;
import fractalzoomer.functions.root_finding_methods.schroder.SchroderPoly;
import fractalzoomer.functions.root_finding_methods.schroder.SchroderCos;
import fractalzoomer.functions.root_finding_methods.schroder.SchroderGeneralized8;
import fractalzoomer.functions.general.SierpinskiGasket;
import fractalzoomer.functions.mandelbrot.MandelbrotWth;
import fractalzoomer.functions.math.Sinh;
import fractalzoomer.functions.math.Sin;
import fractalzoomer.functions.math.Tan;
import fractalzoomer.functions.math.Tanh;
import fractalzoomer.functions.general.Nova;
import fractalzoomer.functions.formulas.general.mathtype.Formula31;
import fractalzoomer.functions.formulas.general.mathtype.Formula30;
import fractalzoomer.functions.formulas.general.mathtype.Formula32;
import fractalzoomer.functions.formulas.general.mathtype.Formula33;
import fractalzoomer.functions.formulas.general.mathtype.Formula34;
import fractalzoomer.functions.formulas.general.mathtype.Formula35;
import fractalzoomer.functions.formulas.general.mathtype.Formula36;
import fractalzoomer.functions.formulas.general.mathtype.Formula37;
import fractalzoomer.functions.formulas.general.newtonvariant.Formula42;
import fractalzoomer.functions.formulas.general.newtonvariant.Formula43;
import fractalzoomer.functions.formulas.general.newtonvariant.Formula44;
import fractalzoomer.functions.formulas.general.newtonvariant.Formula45;
import fractalzoomer.functions.formulas.m_like_generalization.Formula38;
import fractalzoomer.functions.formulas.m_like_generalization.Formula39;
import fractalzoomer.functions.formulas.m_like_generalization.Formula46;
import fractalzoomer.functions.formulas.m_like_generalization.zab_zde_fg.Formula40;
import fractalzoomer.functions.formulas.m_like_generalization.zab_zde_fg.Formula41;
import fractalzoomer.functions.user_formulas.UserFormulaConverging;
import fractalzoomer.functions.user_formulas.UserFormulaEscaping;
import fractalzoomer.functions.user_formulas.UserFormulaIterationBasedConverging;
import fractalzoomer.functions.user_formulas.UserFormulaIterationBasedEscaping;
import fractalzoomer.functions.root_finding_methods.secant.Secant3;
import fractalzoomer.functions.root_finding_methods.secant.Secant4;
import fractalzoomer.functions.root_finding_methods.secant.SecantCos;
import fractalzoomer.functions.root_finding_methods.secant.SecantGeneralized3;
import fractalzoomer.functions.root_finding_methods.secant.SecantGeneralized8;
import fractalzoomer.functions.root_finding_methods.secant.SecantPoly;
import fractalzoomer.functions.root_finding_methods.steffensen.Steffensen3;
import fractalzoomer.functions.root_finding_methods.steffensen.Steffensen4;
import fractalzoomer.functions.root_finding_methods.steffensen.SteffensenGeneralized3;
import fractalzoomer.functions.szegedi_butterfly.SzegediButterfly1;
import fractalzoomer.functions.szegedi_butterfly.SzegediButterfly2;
import fractalzoomer.functions.user_formulas.UserFormulaConditionalConverging;
import fractalzoomer.functions.user_formulas.UserFormulaConditionalEscaping;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBufferInt;
import java.awt.image.Kernel;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hrkalona
 */
public abstract class ThreadDraw extends Thread {

    public static final int NORMAL = 0;
    public static final int FAST_JULIA = 1;
    public static final int COLOR_CYCLING = 2;
    public static final int APPLY_PALETTE_AND_FILTER = 3;
    public static final int JULIA_MAP = 4;
    public static final int ROTATE_3D_MODEL = 5;
    public static final int POLAR = 6;
    public static final int FAST_JULIA_POLAR = 7;
    public static final int JULIA_MAP_POLAR = 8;
    public static final int APPLY_PALETTE_AND_FILTER_3D_MODEL = 9;
    public static final float[] thick_edges = {-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -2.0f, -2.0f, -2.0f, -1.0f, -1.0f, -2.0f, 32.0f, -2.0f, -1.0f, -1.0f, -2.0f, -2.0f, -2.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f};
    public static final float[] thin_edges = {-1.0f, -1.0f, -1.0f, -1.0f, 8.0f, -1.0f, -1.0f, -1.0f, -1.0f};
    public static final float[] sharpness_high = {-0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, 3.4f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f};
    public static float[] sharpness_low = {0.0f, -0.2f, 0.0f, -0.2f, 1.8f, -0.2f, 0.0f, -0.2f, 0.0f};
    public static final float[] EMBOSS = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, -1.0f};
    //public static final float blackBodyRGB[] = {1.0000F, 0.0401F, 0.0000F, 1.0000F, 0.0631F, 0.0000F, 1.0000F, 0.0860F, 0.0000F, 1.0000F, 0.1085F, 0.0000F, 1.0000F, 0.1303F, 0.0000F, 1.0000F, 0.1515F, 0.0000F, 1.0000F, 0.1718F, 0.0000F, 1.0000F, 0.1912F, 0.0000F, 1.0000F, 0.2097F, 0.0000F, 1.0000F, 0.2272F, 0.0000F, 1.0000F, 0.2484F, 0.0061F, 1.0000F, 0.2709F, 0.0153F, 1.0000F, 0.2930F, 0.0257F, 1.0000F, 0.3149F, 0.0373F, 1.0000F, 0.3364F, 0.0501F, 1.0000F, 0.3577F, 0.0640F, 1.0000F, 0.3786F, 0.0790F, 1.0000F, 0.3992F, 0.0950F, 1.0000F, 0.4195F, 0.1119F, 1.0000F, 0.4394F, 0.1297F, 1.0000F, 0.4589F, 0.1483F, 1.0000F, 0.4781F, 0.1677F, 1.0000F, 0.4970F, 0.1879F, 1.0000F, 0.5155F, 0.2087F, 1.0000F, 0.5336F, 0.2301F, 1.0000F, 0.5515F, 0.2520F, 1.0000F, 0.5689F, 0.2745F, 1.0000F, 0.5860F, 0.2974F, 1.0000F, 0.6028F, 0.3207F, 1.0000F, 0.6193F, 0.3444F, 1.0000F, 0.6354F, 0.3684F, 1.0000F, 0.6511F, 0.3927F, 1.0000F, 0.6666F, 0.4172F, 1.0000F, 0.6817F, 0.4419F, 1.0000F, 0.6966F, 0.4668F, 1.0000F, 0.7111F, 0.4919F, 1.0000F, 0.7253F, 0.5170F, 1.0000F, 0.7392F, 0.5422F, 1.0000F, 0.7528F, 0.5675F, 1.0000F, 0.7661F, 0.5928F, 1.0000F, 0.7792F, 0.6180F, 1.0000F, 0.7919F, 0.6433F, 1.0000F, 0.8044F, 0.6685F, 1.0000F, 0.8167F, 0.6937F, 1.0000F, 0.8286F, 0.7187F, 1.0000F, 0.8403F, 0.7437F, 1.0000F, 0.8518F, 0.7686F, 1.0000F, 0.8630F, 0.7933F, 1.0000F, 0.8740F, 0.8179F, 1.0000F, 0.8847F, 0.8424F, 1.0000F, 0.8952F, 0.8666F, 1.0000F, 0.9055F, 0.8907F, 1.0000F, 0.9156F, 0.9147F, 1.0000F, 0.9254F, 0.9384F, 1.0000F, 0.9351F, 0.9619F, 1.0000F, 0.9445F, 0.9853F, 0.9917F, 0.9458F, 1.0000F, 0.9696F, 0.9336F, 1.0000F, 0.9488F, 0.9219F, 1.0000F, 0.9290F, 0.9107F, 1.0000F, 0.9102F, 0.9000F, 1.0000F, 0.8923F, 0.8897F, 1.0000F, 0.8753F, 0.8799F, 1.0000F, 0.8591F, 0.8704F, 1.0000F, 0.8437F, 0.8614F, 1.0000F, 0.8289F, 0.8527F, 1.0000F, 0.8149F, 0.8443F, 1.0000F, 0.8014F, 0.8363F, 1.0000F, 0.7885F, 0.8285F, 1.0000F, 0.7762F, 0.8211F, 1.0000F, 0.7644F, 0.8139F, 1.0000F, 0.7531F, 0.8069F, 1.0000F, 0.7423F, 0.8002F, 1.0000F, 0.7319F, 0.7938F, 1.0000F, 0.7219F, 0.7875F, 1.0000F, 0.7123F, 0.7815F, 1.0000F, 0.7030F, 0.7757F, 1.0000F, 0.6941F, 0.7700F, 1.0000F, 0.6856F, 0.7645F, 1.0000F, 0.6773F, 0.7593F, 1.0000F, 0.6693F, 0.7541F, 1.0000F, 0.6617F, 0.7492F, 1.0000F, 0.6543F, 0.7444F, 1.0000F, 0.6471F, 0.7397F, 1.0000F, 0.6402F, 0.7352F, 1.0000F, 0.6335F, 0.7308F, 1.0000F, 0.6271F, 0.7265F, 1.0000F, 0.6208F, 0.7224F, 1.0000F, 0.6148F, 0.7183F, 1.0000F, 0.6089F, 0.7144F, 1.0000F, 0.6033F, 0.7106F, 1.0000F, 0.5978F, 0.7069F, 1.0000F, 0.5925F, 0.7033F, 1.0000F, 0.5873F, 0.6998F, 1.0000F, 0.5823F, 0.6964F, 1.0000F, 0.5774F, 0.6930F, 1.0000F, 0.5727F, 0.6898F, 1.0000F, 0.5681F, 0.6866F, 1.0000F, 0.5637F, 0.6836F, 1.0000F, 0.5593F, 0.6806F, 1.0000F, 0.5551F, 0.6776F, 1.0000F, 0.5510F, 0.6748F, 1.0000F, 0.5470F, 0.6720F, 1.0000F, 0.5432F, 0.6693F, 1.0000F, 0.5394F, 0.6666F, 1.0000F, 0.5357F, 0.6640F, 1.0000F, 0.5322F, 0.6615F, 1.0000F, 0.5287F, 0.6590F, 1.0000F, 0.5253F, 0.6566F, 1.0000F, 0.5220F, 0.6542F, 1.0000F, 0.5187F, 0.6519F, 1.0000F, 0.5156F, 0.6497F, 1.0000F, 0.5125F, 0.6474F, 1.0000F, 0.5095F, 0.6453F, 1.0000F, 0.5066F, 0.6432F, 1.0000F, 0.5037F, 0.6411F, 1.0000F, 0.5009F, 0.6391F, 1.0000F, 0.4982F, 0.6371F, 1.0000F, 0.4955F, 0.6351F, 1.0000F, 0.4929F, 0.6332F, 1.0000F, 0.4904F, 0.6314F, 1.0000F, 0.4879F, 0.6295F, 1.0000F, 0.4854F, 0.6277F, 1.0000F, 0.4831F, 0.6260F, 1.0000F, 0.4807F, 0.6243F, 1.0000F, 0.4785F, 0.6226F, 1.0000F, 0.4762F, 0.6209F, 1.0000F, 0.4740F, 0.6193F, 1.0000F, 0.4719F, 0.6177F, 1.0000F, 0.4698F, 0.6161F, 1.0000F, 0.4677F, 0.6146F, 1.0000F, 0.4657F, 0.6131F, 1.0000F, 0.4638F, 0.6116F, 1.0000F, 0.4618F, 0.6102F, 1.0000F, 0.4599F, 0.6087F, 1.0000F, 0.4581F, 0.6073F, 1.0000F, 0.4563F, 0.6060F, 1.0000F, 0.4545F, 0.6046F, 1.0000F, 0.4527F, 0.6033F, 1.0000F, 0.4510F, 0.6020F, 1.0000F, 0.4493F, 0.6007F, 1.0000F, 0.4477F, 0.5994F, 1.0000F, 0.4460F, 0.5982F, 1.0000F, 0.4445F, 0.5970F, 1.0000F, 0.4429F, 0.5958F, 1.0000F, 0.4413F, 0.5946F, 1.0000F, 0.4398F, 0.5935F, 1.0000F, 0.4384F, 0.5923F, 1.0000F, 0.4369F, 0.5912F, 1.0000F, 0.4355F, 0.5901F, 1.0000F, 0.4341F, 0.5890F, 1.0000F, 0.4327F, 0.5879F, 1.0000F, 0.4313F, 0.5869F, 1.0000F, 0.4300F, 0.5859F, 1.0000F, 0.4287F, 0.5848F, 1.0000F, 0.4274F, 0.5838F, 1.0000F, 0.4261F, 0.5829F, 1.0000F, 0.4249F, 0.5819F, 1.0000F, 0.4236F, 0.5809F, 1.0000F, 0.4224F, 0.5800F, 1.0000F, 0.4212F, 0.5791F, 1.0000F, 0.4201F, 0.5781F, 1.0000F, 0.4189F, 0.5772F, 1.0000F, 0.4178F, 0.5763F, 1.0000F, 0.4167F, 0.5755F, 1.0000F, 0.4156F, 0.5746F, 1.0000F, 0.4145F, 0.5738F, 1.0000F, 0.4134F, 0.5729F, 1.0000F, 0.4124F, 0.5721F, 1.0000F, 0.4113F, 0.5713F, 1.0000F, 0.4103F, 0.5705F, 1.0000F, 0.4093F, 0.5697F, 1.0000F, 0.4083F, 0.5689F, 1.0000F, 0.4074F, 0.5681F, 1.0000F, 0.4064F, 0.5674F, 1.0000F, 0.4055F, 0.5666F, 1.0000F, 0.4045F, 0.5659F, 1.0000F, 0.4036F, 0.5652F, 1.0000F, 0.4027F, 0.5644F, 1.0000F, 0.4018F, 0.5637F, 1.0000F, 0.4009F, 0.5630F, 1.0000F, 0.4001F, 0.5623F, 1.0000F, 0.3992F, 0.5616F, 1.0000F, 0.3984F, 0.5610F, 1.0000F, 0.3975F, 0.5603F, 1.0000F, 0.3967F, 0.5596F, 1.0000F, 0.3959F, 0.5590F, 1.0000F, 0.3951F, 0.5584F, 1.0000F, 0.3943F, 0.5577F, 1.0000F, 0.3935F, 0.5571F, 1.0000F, 0.3928F, 0.5565F, 1.0000F, 0.3920F, 0.5559F, 1.0000F, 0.3913F, 0.5553F, 1.0000F, 0.3905F, 0.5547F, 1.0000F, 0.3898F, 0.5541F, 1.0000F, 0.3891F, 0.5535F, 1.0000F, 0.3884F, 0.5529F, 1.0000F, 0.3877F, 0.5524F, 1.0000F, 0.3870F, 0.5518F, 1.0000F, 0.3863F, 0.5513F, 1.0000F, 0.3856F, 0.5507F, 1.0000F, 0.3850F, 0.5502F, 1.0000F, 0.3843F, 0.5496F, 1.0000F, 0.3836F, 0.5491F, 1.0000F, 0.3830F, 0.5486F, 1.0000F, 0.3824F, 0.5481F, 1.0000F, 0.3817F, 0.5476F, 1.0000F, 0.3811F, 0.5471F, 1.0000F, 0.3805F, 0.5466F, 1.0000F, 0.3799F, 0.5461F, 1.0000F, 0.3793F, 0.5456F, 1.0000F, 0.3787F, 0.5451F, 1.0000F, 0.3781F, 0.5446F, 1.0000F, 0.3776F, 0.5441F, 1.0000F, 0.3770F, 0.5437F, 1.0000F, 0.3764F, 0.5432F, 1.0000F, 0.3759F, 0.5428F, 1.0000F, 0.3753F, 0.5423F, 1.0000F, 0.3748F, 0.5419F, 1.0000F, 0.3742F, 0.5414F, 1.0000F, 0.3737F, 0.5410F, 1.0000F, 0.3732F, 0.5405F, 1.0000F, 0.3726F, 0.5401F, 1.0000F, 0.3721F, 0.5397F, 1.0000F, 0.3716F, 0.5393F, 1.0000F, 0.3711F, 0.5389F, 1.0000F, 0.3706F, 0.5384F, 1.0000F, 0.3701F, 0.5380F, 1.0000F, 0.3696F, 0.5376F, 1.0000F, 0.3692F, 0.5372F, 1.0000F, 0.3687F, 0.5368F, 1.0000F, 0.3682F, 0.5365F, 1.0000F, 0.3677F, 0.5361F, 1.0000F, 0.3673F, 0.5357F, 1.0000F, 0.3668F, 0.5353F, 1.0000F, 0.3664F, 0.5349F, 1.0000F, 0.3659F, 0.5346F, 1.0000F, 0.3655F, 0.5342F, 1.0000F, 0.3650F, 0.5338F, 1.0000F, 0.3646F, 0.5335F, 1.0000F, 0.3642F, 0.5331F, 1.0000F, 0.3637F, 0.5328F, 1.0000F, 0.3633F, 0.5324F, 1.0000F, 0.3629F, 0.5321F, 1.0000F, 0.3625F, 0.5317F, 1.0000F, 0.3621F, 0.5314F, 1.0000F, 0.3617F, 0.5310F, 1.0000F, 0.3613F, 0.5307F, 1.0000F, 0.3609F, 0.5304F, 1.0000F, 0.3605F, 0.5300F, 1.0000F, 0.3601F, 0.5297F, 1.0000F, 0.3597F, 0.5294F, 1.0000F, 0.3593F, 0.5291F, 1.0000F, 0.3589F, 0.5288F, 1.0000F, 0.3586F, 0.5284F, 1.0000F, 0.3582F, 0.5281F, 1.0000F, 0.3578F, 0.5278F, 1.0000F, 0.3575F, 0.5275F, 1.0000F, 0.3571F, 0.5272F, 1.0000F, 0.3567F, 0.5269F, 1.0000F, 0.3564F, 0.5266F, 1.0000F, 0.3560F, 0.5263F, 1.0000F, 0.3557F, 0.5260F, 1.0000F, 0.3553F, 0.5257F, 1.0000F, 0.3550F, 0.5255F, 1.0000F, 0.3546F, 0.5252F, 1.0000F, 0.3543F, 0.5249F, 1.0000F, 0.3540F, 0.5246F, 1.0000F, 0.3536F, 0.5243F, 1.0000F, 0.3533F, 0.5241F, 1.0000F, 0.3530F, 0.5238F, 1.0000F, 0.3527F, 0.5235F, 1.0000F, 0.3524F, 0.5232F, 1.0000F, 0.3520F, 0.5230F, 1.0000F, 0.3517F, 0.5227F, 1.0000F, 0.3514F, 0.5225F, 1.0000F, 0.3511F, 0.5222F, 1.0000F, 0.3508F, 0.5219F, 1.0000F, 0.3505F, 0.5217F, 1.0000F, 0.3502F, 0.5214F, 1.0000F, 0.3499F, 0.5212F, 1.0000F, 0.3496F, 0.5209F, 1.0000F, 0.3493F, 0.5207F, 1.0000F, 0.3490F, 0.5204F, 1.0000F, 0.3487F, 0.5202F, 1.0000F, 0.3485F, 0.5200F, 1.0000F, 0.3482F, 0.5197F, 1.0000F, 0.3479F, 0.5195F, 1.0000F, 0.3476F, 0.5192F, 1.0000F, 0.3473F, 0.5190F, 1.0000F, 0.3471F, 0.5188F, 1.0000F, 0.3468F, 0.5186F, 1.0000F, 0.3465F, 0.5183F, 1.0000F, 0.3463F, 0.5181F, 1.0000F, 0.3460F, 0.5179F, 1.0000F, 0.3457F, 0.5177F, 1.0000F, 0.3455F, 0.5174F, 1.0000F, 0.3452F, 0.5172F, 1.0000F, 0.3450F, 0.5170F, 1.0000F, 0.3447F, 0.5168F, 1.0000F, 0.3444F, 0.5166F, 1.0000F, 0.3442F, 0.5164F, 1.0000F, 0.3439F, 0.5161F, 1.0000F, 0.3437F, 0.5159F, 1.0000F, 0.3435F, 0.5157F, 1.0000F, 0.3432F, 0.5155F, 1.0000F, 0.3430F, 0.5153F, 1.0000F, 0.3427F, 0.5151F, 1.0000F, 0.3425F, 0.5149F, 1.0000F, 0.3423F, 0.5147F, 1.0000F, 0.3420F, 0.5145F, 1.0000F, 0.3418F, 0.5143F, 1.0000F, 0.3416F, 0.5141F, 1.0000F, 0.3413F, 0.5139F, 1.0000F, 0.3411F, 0.5137F, 1.0000F, 0.3409F, 0.5135F, 1.0000F, 0.3407F, 0.5133F, 1.0000F, 0.3404F, 0.5132F, 1.0000F, 0.3402F, 0.5130F, 1.0000F, 0.3400F, 0.5128F, 1.0000F, 0.3398F, 0.5126F, 1.0000F, 0.3396F, 0.5124F, 1.0000F, 0.3393F, 0.5122F, 1.0000F, 0.3391F, 0.5120F, 1.0000F, 0.3389F, 0.5119F, 1.0000F, 0.3387F, 0.5117F, 1.0000F, 0.3385F, 0.5115F, 1.0000F, 0.3383F, 0.5113F, 1.0000F, 0.3381F, 0.5112F, 1.0000F, 0.3379F, 0.5110F, 1.0000F, 0.3377F, 0.5108F, 1.0000F, 0.3375F, 0.5106F, 1.0000F, 0.3373F, 0.5105F, 1.0000F, 0.3371F, 0.5103F, 1.0000F, 0.3369F, 0.5101F, 1.0000F, 0.3367F, 0.5100F, 1.0000F, 0.3365F, 0.5098F, 1.0000F, 0.3363F, 0.5096F, 1.0000F, 0.3361F, 0.5095F, 1.0000F, 0.3359F, 0.5093F, 1.0000F, 0.3357F, 0.5091F, 1.0000F, 0.3356F, 0.5090F, 1.0000F, 0.3354F, 0.5088F, 1.0000F, 0.3352F, 0.5087F, 1.0000F, 0.3350F, 0.5085F, 1.0000F, 0.3348F, 0.5084F, 1.0000F, 0.3346F, 0.5082F, 1.0000F, 0.3345F, 0.5080F, 1.0000F, 0.3343F, 0.5079F, 1.0000F, 0.3341F, 0.5077F, 1.0000F, 0.3339F, 0.5076F, 1.0000F, 0.3338F, 0.5074F, 1.0000F, 0.3336F, 0.5073F, 1.0000F, 0.3334F, 0.5071F, 1.0000F, 0.3332F, 0.5070F, 1.0000F, 0.3331F, 0.5068F, 1.0000F, 0.3329F, 0.5067F, 1.0000F, 0.3327F, 0.5066F, 1.0000F, 0.3326F, 0.5064F, 1.0000F, 0.3324F, 0.5063F, 1.0000F, 0.3322F, 0.5061F, 1.0000F, 0.3321F, 0.5060F, 1.0000F, 0.3319F, 0.5058F, 1.0000F, 0.3317F, 0.5057F, 1.0000F, 0.3316F, 0.5056F, 1.0000F, 0.3314F, 0.5054F, 1.0000F, 0.3313F, 0.5053F, 1.0000F, 0.3311F, 0.5052F, 1.0000F, 0.3309F, 0.5050F, 1.0000F, 0.3308F, 0.5049F, 1.0000F, 0.3306F, 0.5048F, 1.0000F, 0.3305F, 0.5046F, 1.0000F, 0.3303F, 0.5045F, 1.0000F, 0.3302F, 0.5044F, 1.0000F, 0.3300F, 0.5042F, 1.0000F, 0.3299F, 0.5041F, 1.0000F, 0.3297F, 0.5040F, 1.0000F, 0.3296F, 0.5038F, 1.0000F, 0.3294F, 0.5037F, 1.0000F, 0.3293F, 0.5036F, 1.0000F, 0.3291F, 0.5035F, 1.0000F, 0.3290F, 0.5033F, 1.0000F, 0.3288F, 0.5032F, 1.0000F, 0.3287F, 0.5031F, 1.0000F, 0.3286F, 0.5030F, 1.0000F, 0.3284F, 0.5028F, 1.0000F, 0.3283F, 0.5027F, 1.0000F, 0.3281F, 0.5026F, 1.0000F, 0.3280F, 0.5025F, 1.0000F, 0.3279F, 0.5024F, 1.0000F, 0.3277F, 0.5022F, 1.0000F};
    public static final int[] colors_3d = {-16777216, -16711423, -16645630, -16579837, -16514044, -16448251, -16382458, -16316665, -16250872, -16185079, -16119286, -16053493, -15987700, -15921907, -15856114, -15790321, -15724528, -15658735, -15592942, -15527149, -15461356, -15395563, -15329770, -15263977, -15198184, -15132391, -15066598, -15000805, -14935012, -14869219, -14803426, -14737633, -14671840, -14606047, -14540254, -14474461, -14408668, -14342875, -14277082, -14211289, -14145496, -14079703, -14013910, -13948117, -13882324, -13816531, -13750738, -13684945, -13619152, -13553359, -13487566, -13421773, -13355980, -13290187, -13224394, -13158601, -13092808, -13027015, -12961222, -12895429, -12829636, -12763843, -12698050, -12632257, -12566464, -12500671, -12434878, -12369085, -12303292, -12237499, -12171706, -12105913, -12040120, -11974327, -11908534, -11842741, -11776948, -11711155, -11645362, -11579569, -11513776, -11447983, -11382190, -11316397, -11250604, -11184811, -11119018, -11053225, -10987432, -10921639, -10855846, -10790053, -10724260, -10658467, -10592674, -10526881, -10461088, -10395295, -10329502, -10263709, -10197916, -10132123, -10066330, -10000537, -9934744, -9868951, -9803158, -9737365, -9671572, -9605779, -9539986, -9474193, -9408400, -9342607, -9276814, -9211021, -9145228, -9079435, -9013642, -8947849, -8882056, -8816263, -8750470, -8684677, -8618884, -8553091, -8487298, -8421505, -8355712, -8289919, -8224126, -8158333, -8092540, -8026747, -7960954, -7895161, -7829368, -7763575, -7697782, -7631989, -7566196, -7500403, -7434610, -7368817, -7303024, -7237231, -7171438, -7105645, -7039852, -6974059, -6908266, -6842473, -6776680, -6710887, -6645094, -6579301, -6513508, -6447715, -6381922, -6316129, -6250336, -6184543, -6118750, -6052957, -5987164, -5921371, -5855578, -5789785, -5723992, -5658199, -5592406, -5526613, -5460820, -5395027, -5329234, -5263441, -5197648, -5131855, -5066062, -5000269, -4934476, -4868683, -4802890, -4737097, -4671304, -4605511, -4539718, -4473925, -4408132, -4342339, -4276546, -4210753, -4144960, -4079167, -4013374, -3947581, -3881788, -3815995, -3750202, -3684409, -3618616, -3552823, -3487030, -3421237, -3355444, -3289651, -3223858, -3158065, -3092272, -3026479, -2960686, -2894893, -2829100, -2763307, -2697514, -2631721, -2565928, -2500135, -2434342, -2368549, -2302756, -2236963, -2171170, -2105377, -2039584, -1973791, -1907998, -1842205, -1776412, -1710619, -1644826, -1579033, -1513240, -1447447, -1381654, -1315861, -1250068, -1184275, -1118482, -1052689, -986896, -921103, -855310, -789517, -723724, -657931, -592138, -526345, -460552, -394759, -328966, -263173, -197380, -131587, -65794, -1};
    protected static double[] image_iterations;
    protected static AtomicInteger synchronization;
    protected static AtomicInteger synchronization2;
    protected static AtomicInteger synchronization3;
    protected static AtomicInteger total_calculated;
    protected static AtomicInteger normal_drawing_algorithm_pixel;
    protected int[] rgbs;
    protected Fractal fractal;
    protected PaletteColor palette_color;
    protected int FROMx;
    protected int TOx;
    protected int FROMy;
    protected int TOy;
    protected boolean[] filters;
    protected boolean julia;
    protected boolean fast_julia_filters;
    protected boolean boundary_tracing;
    protected boolean d3;
    protected MainWindow ptr;
    protected BufferedImage image;
    protected int drawing_done;
    protected int thread_calculated;
    protected int fractal_color;
    protected int max_iterations;
    protected boolean color_cycling;
    protected int color_cycling_location;
    protected int action;
    protected int thread_slices;
    protected boolean bump_map;
    protected double bumpMappingStrength;
    protected double bumpMappingDepth;
    protected double lightDirectionDegrees;
    protected boolean fake_de;
    protected double fake_de_factor;
    protected static double[] image_iterations_fast_julia;
    protected double height_ratio;
    protected boolean polar_projection;
    protected double circle_period;
    protected double x_antialiasing_size;
    protected double x_antialiasing_size_x2;
    protected double y_antialiasing_size;
    protected double y_antialiasing_size_x2;
    protected int[] filters_options_vals;
    protected int detail;
    protected double fiX, fiY, scale, m20, m21, m22;
    protected double d3_height_scale;
    protected double d3_height_offset;
    protected static float vert[][][];
    protected static float vert1[][][];
    protected static float Norm[][][][];
    protected static float Norm1z[][][];
    //protected int d3_draw_method;
    protected double color_3d_blending;
    public static final int MAX_BUMP_MAPPING_DEPTH = 100;
    public static final int DEFAULT_BUMP_MAPPING_STRENGTH = 50;

    /*protected double[] AntialiasingData;
    protected double[] FastJuliaData;
    protected int[] Done;
    protected int QueueSize;
    protected int[] Queue;
    protected int QueueHead;
    protected int QueueTail;
    public static final int Loaded = 1, Queued = 2;*/
    static {

        synchronization = new AtomicInteger(0);
        synchronization2 = new AtomicInteger(0);
        synchronization3 = new AtomicInteger(0);
        total_calculated = new AtomicInteger(0);
        normal_drawing_algorithm_pixel = new AtomicInteger(0);
        image_iterations = new double[MainWindow.image_size * MainWindow.image_size];
        image_iterations_fast_julia = new double[MainWindow.FAST_JULIA_IMAGE_SIZE * MainWindow.FAST_JULIA_IMAGE_SIZE];

    }

    //Fractal
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout,  String bailout_test_user_formula, int bailout_test_comparison, double n_norm, boolean d3, int detail, double fiX, double fiY, double color_3d_blending, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double d3_height_scale, double d3_height_offset, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.filters = filters;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.color_cycling_location = color_cycling_location;
        this.filters_options_vals = filters_options_vals;
        this.d3 = d3;
        //this.d3_draw_method = d3_draw_method;
        this.detail = detail;
        this.fiX = fiX;
        this.fiY = fiY;
        this.height_ratio = height_ratio;
        this.d3_height_scale = d3_height_scale;
        this.d3_height_offset = d3_height_offset;
        scale = 1;
        this.color_3d_blending = color_3d_blending;

        this.bump_map = bump_map;
        this.bumpMappingStrength = bumpMappingStrength;
        this.bumpMappingDepth = bumpMappingDepth;
        this.lightDirectionDegrees = lightDirectionDegrees;

        this.fake_de = fake_de;
        this.fake_de_factor = fake_de_factor;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;


        if(filters[MainWindow.ANTIALIASING]) {
            if(d3 && polar_projection) {
                int n1 = detail - 1;

                y_antialiasing_size = ((2 * circle_period * Math.PI) / n1) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
            else if(polar_projection) {
                y_antialiasing_size = ((2 * circle_period * Math.PI) / image.getHeight()) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
            else if(d3) {
                int n1 = detail - 1;

                x_antialiasing_size = (size / n1) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / n1) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
            else {
                x_antialiasing_size = (size / image.getHeight()) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / image.getHeight()) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
        }
        // thread_slices = 10;

        if(polar_projection) {
            action = POLAR;
        }
        else {
            action = NORMAL;
        }

        julia = false;

        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();



        this.boundary_tracing = boundary_tracing;

        /*if(boundary_tracing) {
        int width = TOx - FROMx;
        int height = TOy - FROMy;
        QueueSize = (width + height) * 4;
        Queue = new int[QueueSize];
        Done = new int[width * height];
        QueueHead = 0;
        QueueTail = 0;
        
        if(filters[MainWindow.ANTIALIASING]) {
        AntialiasingData = new double[width * height];
        }
        }*/

        switch (function) {
            case 0:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, exterior_de, exterior_de_factor, escaping_smooth_algorithm);
                break;
            case 1:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case 2:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case 3:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case 4:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case 5:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case 6:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case 7:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case 8:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm);
                break;
            case MainWindow.NEWTON3:
                fractal = new Newton3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.NEWTON4:
                fractal = new Newton4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.NEWTONGENERALIZED3:
                fractal = new NewtonGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.NEWTONGENERALIZED8:
                fractal = new NewtonGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.NEWTONSIN:
                fractal = new NewtonSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.NEWTONCOS:
                fractal = new NewtonCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.NEWTONPOLY:
                fractal = new NewtonPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.SIERPINSKI_GASKET:
                fractal = new SierpinskiGasket(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.HALLEY3:
                fractal = new Halley3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HALLEY4:
                fractal = new Halley4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HALLEYGENERALIZED3:
                fractal = new HalleyGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HALLEYGENERALIZED8:
                fractal = new HalleyGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HALLEYSIN:
                fractal = new HalleySin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HALLEYCOS:
                fractal = new HalleyCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HALLEYPOLY:
                fractal = new HalleyPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SCHRODER3:
                fractal = new Schroder3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SCHRODER4:
                fractal = new Schroder4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SCHRODERGENERALIZED3:
                fractal = new SchroderGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SCHRODERGENERALIZED8:
                fractal = new SchroderGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SCHRODERSIN:
                fractal = new SchroderSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SCHRODERCOS:
                fractal = new SchroderCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SCHRODERPOLY:
                fractal = new SchroderPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HOUSEHOLDER3:
                fractal = new Householder3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HOUSEHOLDER4:
                fractal = new Householder4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HOUSEHOLDERGENERALIZED3:
                fractal = new HouseholderGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HOUSEHOLDERGENERALIZED8:
                fractal = new HouseholderGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HOUSEHOLDERSIN:
                fractal = new HouseholderSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HOUSEHOLDERCOS:
                fractal = new HouseholderCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HOUSEHOLDERPOLY:
                fractal = new HouseholderPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SECANT3:
                fractal = new Secant3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SECANT4:
                fractal = new Secant4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SECANTGENERALIZED3:
                fractal = new SecantGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SECANTGENERALIZED8:
                fractal = new SecantGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SECANTCOS:
                fractal = new SecantCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SECANTPOLY:
                fractal = new SecantPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.STEFFENSEN3:
                fractal = new Steffensen3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.STEFFENSEN4:
                fractal = new Steffensen4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.STEFFENSENGENERALIZED3:
                fractal = new SteffensenGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.NOVA:
                fractal = new Nova(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, z_exponent_nova, relaxation, nova_method, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.EXP:
                fractal = new Exp(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.LOG:
                fractal = new Log(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.SIN:
                fractal = new Sin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.COS:
                fractal = new Cos(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.TAN:
                fractal = new Tan(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.COT:
                fractal = new Cot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.COTH:
                fractal = new Coth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA30:
                fractal = new Formula30(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA31:
                fractal = new Formula31(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA27:
                fractal = new Formula27(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA28:
                fractal = new Formula28(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA29:
                fractal = new Formula29(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA32:
                fractal = new Formula32(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA33:
                fractal = new Formula33(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA34:
                fractal = new Formula34(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA35:
                fractal = new Formula35(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA36:
                fractal = new Formula36(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA37:
                fractal = new Formula37(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA38:
                fractal = new Formula38(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA39:
                fractal = new Formula39(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA40:
                fractal = new Formula40(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA41:
                fractal = new Formula41(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA42:
                fractal = new Formula42(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA43:
                fractal = new Formula43(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA44:
                fractal = new Formula44(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA45:
                fractal = new Formula45(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA46:
                fractal = new Formula46(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.USER_FORMULA:
                if(bail_technique == 0) {
                    fractal = new UserFormulaEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_formula, user_formula2, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                }
                else {
                    fractal = new UserFormulaConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_formula, user_formula2, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if(bail_technique == 0) {
                    fractal = new UserFormulaIterationBasedEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_formula_iteration_based, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                }
                else {
                    fractal = new UserFormulaIterationBasedConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_formula_iteration_based, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if(bail_technique == 0) {
                    fractal = new UserFormulaConditionalEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_formula_conditions, user_formula_condition_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                }
                else {
                    fractal = new UserFormulaConditionalConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_formula_conditions, user_formula_condition_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;

        }

        drawing_done = 0;
        thread_calculated = 0;

    }

    //Julia
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout,  String bailout_test_user_formula, int bailout_test_comparison, double n_norm, boolean d3, int detail, double fiX, double fiY, double color_3d_blending, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double d3_height_scale, double d3_height_offset, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, double xJuliaCenter, double yJuliaCenter) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.filters = filters;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.color_cycling_location = color_cycling_location;
        this.filters_options_vals = filters_options_vals;
        this.d3 = d3;
        //this.d3_draw_method = d3_draw_method;
        this.detail = detail;
        this.fiX = fiX;
        this.fiY = fiY;
        this.height_ratio = height_ratio;
        this.d3_height_scale = d3_height_scale;
        this.d3_height_offset = d3_height_offset;
        scale = 1;
        this.color_3d_blending = color_3d_blending;

        this.bump_map = bump_map;
        this.bumpMappingStrength = bumpMappingStrength;
        this.bumpMappingDepth = bumpMappingDepth;
        this.lightDirectionDegrees = lightDirectionDegrees;

        this.fake_de = fake_de;
        this.fake_de_factor = fake_de_factor;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        if(filters[MainWindow.ANTIALIASING]) {
            if(d3 && polar_projection) {
                int n1 = detail - 1;

                y_antialiasing_size = ((2 * circle_period * Math.PI) / n1) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
            else if(polar_projection) {
                y_antialiasing_size = ((2 * circle_period * Math.PI) / image.getHeight()) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
            else if(d3) {
                int n1 = detail - 1;

                x_antialiasing_size = (size / n1) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / n1) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
            else {
                x_antialiasing_size = (size / image.getHeight()) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / image.getHeight()) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
        }
        //thread_slices = 10;

        if(polar_projection) {
            action = POLAR;
        }
        else {
            action = NORMAL;
        }

        julia = true;

        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        this.boundary_tracing = boundary_tracing;


        switch (function) {
            case 0:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, exterior_de, exterior_de_factor, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 1:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 2:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 3:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 4:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 5:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 6:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 7:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 8:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.NOVA:
                fractal = new Nova(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, z_exponent_nova, relaxation, nova_method, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.EXP:
                fractal = new Exp(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LOG:
                fractal = new Log(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SIN:
                fractal = new Sin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COS:
                fractal = new Cos(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TAN:
                fractal = new Tan(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COT:
                fractal = new Cot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COTH:
                fractal = new Coth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA30:
                fractal = new Formula30(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA31:
                fractal = new Formula31(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA27:
                fractal = new Formula27(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA28:
                fractal = new Formula28(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA29:
                fractal = new Formula29(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA32:
                fractal = new Formula32(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA33:
                fractal = new Formula33(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA34:
                fractal = new Formula34(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA35:
                fractal = new Formula35(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA36:
                fractal = new Formula36(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA37:
                fractal = new Formula37(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA38:
                fractal = new Formula38(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA39:
                fractal = new Formula39(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA40:
                fractal = new Formula40(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA41:
                fractal = new Formula41(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA42:
                fractal = new Formula42(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA43:
                fractal = new Formula43(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA44:
                fractal = new Formula44(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA45:
                fractal = new Formula45(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA46:
                fractal = new Formula46(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.USER_FORMULA:
                if(bail_technique == 0) {
                    fractal = new UserFormulaEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if(bail_technique == 0) {
                    fractal = new UserFormulaIterationBasedEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaIterationBasedConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if(bail_technique == 0) {
                    fractal = new UserFormulaConditionalEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaConditionalConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;

        }

        drawing_done = 0;
        thread_calculated = 0;

    }

    //Julia Map
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout,  String bailout_test_user_formula, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.filters = filters;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.color_cycling_location = color_cycling_location;
        this.filters_options_vals = filters_options_vals;
        this.height_ratio = height_ratio;

        this.bump_map = bump_map;
        this.bumpMappingStrength = bumpMappingStrength;
        this.bumpMappingDepth = bumpMappingDepth;
        this.lightDirectionDegrees = lightDirectionDegrees;

        this.fake_de = fake_de;
        this.fake_de_factor = fake_de_factor;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        if(filters[MainWindow.ANTIALIASING]) {
            if(polar_projection) {
                y_antialiasing_size = ((2 * circle_period * Math.PI) / (TOx - FROMx)) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
            else {
                x_antialiasing_size = (size / (TOx - FROMx)) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / (TOx - FROMx)) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
        }

        if(polar_projection) {
            action = JULIA_MAP_POLAR;
        }
        else {
            action = JULIA_MAP;
        }

        double xJuliaCenter, yJuliaCenter;

        if(polar_projection) {
            double start;
            double center = Math.log(size);

            double f, sf, cf, r;
            double muly = (2 * circle_period * Math.PI) / image.getHeight();

            double mulx = muly * height_ratio;
            
            start = center - mulx * image.getHeight() / 2.0;

            f = ((TOy + FROMy) * 0.5) * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(((TOx + FROMx) * 0.5) * mulx + start);

            double temp1 = xCenter + r * cf - rotation_center[0];
            double temp2 = yCenter + r * sf - rotation_center[1];

            xJuliaCenter = temp1 * rotation_vals[0] - temp2 * rotation_vals[1] + rotation_center[0];

            yJuliaCenter = temp1 * rotation_vals[1] + temp2 * rotation_vals[0] + rotation_center[1];
        }
        else {
            double size_2_x = size * 0.5;
            double size_2_y = (size * height_ratio) * 0.5;

            double temp_xcenter_size = xCenter - size_2_x;
            double temp_ycenter_size = yCenter - size_2_y;
            double temp_size_image_size_x = size / image.getHeight();
            double temp_size_image_size_y = (size * height_ratio) / image.getHeight();

            double temp1 = temp_xcenter_size + temp_size_image_size_x * ((TOx + FROMx) * 0.5) - rotation_center[0];
            double temp2 = temp_ycenter_size + temp_size_image_size_y * ((TOy + FROMy) * 0.5) - rotation_center[1];

            xJuliaCenter = temp1 * rotation_vals[0] - temp2 * rotation_vals[1] + rotation_center[0];
            yJuliaCenter = temp1 * rotation_vals[1] + temp2 * rotation_vals[0] + rotation_center[1];
        }

        julia = true;

        switch (function) {
            case 0:
                fractal = new Mandelbrot(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, exterior_de, exterior_de_factor, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 1:
                fractal = new MandelbrotCubed(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 2:
                fractal = new MandelbrotFourth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 3:
                fractal = new MandelbrotFifth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 4:
                fractal = new MandelbrotSixth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 5:
                fractal = new MandelbrotSeventh(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 6:
                fractal = new MandelbrotEighth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 7:
                fractal = new MandelbrotNinth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 8:
                fractal = new MandelbrotTenth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(0.5, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.NOVA:
                fractal = new Nova(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, z_exponent_nova, relaxation, nova_method, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.EXP:
                fractal = new Exp(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LOG:
                fractal = new Log(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SIN:
                fractal = new Sin(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COS:
                fractal = new Cos(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TAN:
                fractal = new Tan(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COT:
                fractal = new Cot(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COTH:
                fractal = new Coth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA30:
                fractal = new Formula30(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA31:
                fractal = new Formula31(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA27:
                fractal = new Formula27(-2, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA28:
                fractal = new Formula28(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA29:
                fractal = new Formula29(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA32:
                fractal = new Formula32(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA33:
                fractal = new Formula33(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA34:
                fractal = new Formula34(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA35:
                fractal = new Formula35(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA36:
                fractal = new Formula36(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA37:
                fractal = new Formula37(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA38:
                fractal = new Formula38(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA39:
                fractal = new Formula39(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA40:
                fractal = new Formula40(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA41:
                fractal = new Formula41(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA42:
                fractal = new Formula42(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA43:
                fractal = new Formula43(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA44:
                fractal = new Formula44(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA45:
                fractal = new Formula45(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA46:
                fractal = new Formula46(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.USER_FORMULA:
                if(bail_technique == 0) {
                    fractal = new UserFormulaEscaping(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaConverging(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if(bail_technique == 0) {
                    fractal = new UserFormulaIterationBasedEscaping(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaIterationBasedConverging(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if(bail_technique == 0) {
                    fractal = new UserFormulaConditionalEscaping(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaConditionalConverging(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;

        }

        drawing_done = 0;

    }

    //Julia Preview
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout,  String bailout_test_user_formula, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean[] filters, int[] filters_options_vals, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, double xJuliaCenter, double yJuliaCenter) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.fast_julia_filters = fast_julia_filters;
        this.filters = filters;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.color_cycling_location = color_cycling_location;
        this.filters_options_vals = filters_options_vals;
        this.height_ratio = height_ratio;

        this.bump_map = bump_map;
        this.bumpMappingStrength = bumpMappingStrength;
        this.bumpMappingDepth = bumpMappingDepth;
        this.lightDirectionDegrees = lightDirectionDegrees;

        this.fake_de = fake_de;
        this.fake_de_factor = fake_de_factor;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        if(filters[MainWindow.ANTIALIASING]) {
            if(polar_projection) {
                y_antialiasing_size = ((2 * circle_period * Math.PI) / image.getHeight()) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
            else {
                x_antialiasing_size = (size / image.getHeight()) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / image.getHeight()) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
        }
        //thread_slices = 10;

        if(polar_projection) {
            action = FAST_JULIA_POLAR;
        }
        else {
            action = FAST_JULIA;
        }

        julia = true;

        this.boundary_tracing = boundary_tracing;


        switch (function) {
            case 0:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, exterior_de, exterior_de_factor, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 1:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 2:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 3:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 4:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 5:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 6:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 7:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case 8:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.NOVA:
                fractal = new Nova(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, z_exponent_nova, relaxation, nova_method, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.EXP:
                fractal = new Exp(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LOG:
                fractal = new Log(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SIN:
                fractal = new Sin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COS:
                fractal = new Cos(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TAN:
                fractal = new Tan(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COT:
                fractal = new Cot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COTH:
                fractal = new Coth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA30:
                fractal = new Formula30(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA31:
                fractal = new Formula31(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA27:
                fractal = new Formula27(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA28:
                fractal = new Formula28(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA29:
                fractal = new Formula29(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA32:
                fractal = new Formula32(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA33:
                fractal = new Formula33(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA34:
                fractal = new Formula34(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA35:
                fractal = new Formula35(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA36:
                fractal = new Formula36(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA37:
                fractal = new Formula37(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA38:
                fractal = new Formula38(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA39:
                fractal = new Formula39(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA40:
                fractal = new Formula40(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA41:
                fractal = new Formula41(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA42:
                fractal = new Formula42(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA43:
                fractal = new Formula43(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA44:
                fractal = new Formula44(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA45:
                fractal = new Formula45(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA46:
                fractal = new Formula46(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.USER_FORMULA:
                if(bail_technique == 0) {
                    fractal = new UserFormulaEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if(bail_technique == 0) {
                    fractal = new UserFormulaIterationBasedEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaIterationBasedConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if(bail_technique == 0) {
                    fractal = new UserFormulaConditionalEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaConditionalConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula,  user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;

        }

    }

    //Color Cycling
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, BufferedImage image, int color_cycling_location, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, boolean fake_de, double fake_de_factor) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.max_iterations = max_iterations;
        this.image = image;
        this.color_cycling_location = color_cycling_location;
        this.fractal_color = fractal_color.getRGB();
        action = COLOR_CYCLING;
        color_cycling = true;

        this.bump_map = bump_map;
        this.bumpMappingStrength = bumpMappingStrength;
        this.bumpMappingDepth = bumpMappingDepth;
        this.lightDirectionDegrees = lightDirectionDegrees;

        this.fake_de = fake_de;
        this.fake_de_factor = fake_de_factor;

        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    }

    //Apply Filter
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, int color_cycling_location, boolean[] filters, int[] filters_options_vals, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, boolean fake_de, double fake_de_factor) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.max_iterations = max_iterations;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.color_cycling_location = color_cycling_location;
        this.filters = filters;
        this.filters_options_vals = filters_options_vals;
        thread_slices = 10;
        action = APPLY_PALETTE_AND_FILTER;

        this.bump_map = bump_map;
        this.bumpMappingStrength = bumpMappingStrength;
        this.bumpMappingDepth = bumpMappingDepth;
        this.lightDirectionDegrees = lightDirectionDegrees;

        this.fake_de = fake_de;
        this.fake_de_factor = fake_de_factor;

        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        drawing_done = 0;

    }

    //Rotate 3d model
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, int detail, double fiX, double fiY, double color_3d_blending, boolean draw_action, MainWindow ptr, BufferedImage image, boolean[] filters, int[] filters_options_vals) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.image = image;
        this.filters = filters;
        this.filters_options_vals = filters_options_vals;
        this.detail = detail;
        this.fiX = fiX;
        this.fiY = fiY;
        //this.d3_draw_method = d3_draw_method;
        this.color_3d_blending = color_3d_blending;

        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        scale = 1;

        if(draw_action) {
            action = ROTATE_3D_MODEL;
        }
        else {
            action = APPLY_PALETTE_AND_FILTER_3D_MODEL;
            drawing_done = 0;
        }
        
    }

    @Override
    public void run() {

        switch (action) {

            case NORMAL:
                draw();
                break;
            case FAST_JULIA:
                fastJuliaDraw();
                break;
            case COLOR_CYCLING:
                colorCycling();
                break;
            case APPLY_PALETTE_AND_FILTER:
                applyPaletteAndFilter();
                break;
            case JULIA_MAP:
                drawJuliaMap();
                break;
            case ROTATE_3D_MODEL:
                rotate3DModel();
                break;   
            case APPLY_PALETTE_AND_FILTER_3D_MODEL:
                applyPaletteAndFilter3DModel();
                break;
            case POLAR:
                drawPolar();
                break;
            case FAST_JULIA_POLAR:
                fastJuliaDrawPolar();
                break;
            case JULIA_MAP_POLAR:
                drawJuliaMapPolar();
                break;


        }

    }

    private void draw() {

        int image_size = image.getHeight();

        if(julia) {
            if(d3) {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawJulia3DAntialiased(image_size);
                }
                else {
                    drawJulia3D(image_size);
                }
            }
            else if(bump_map || fake_de) {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawJuliaAntialiasedPostProcess(image_size);
                }
                else {
                    drawJuliaPostProcess(image_size);
                }
            }
            else {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawJuliaAntialiased(image_size);
                }
                else {
                    drawJulia(image_size);
                }
            }
        }
        else {
            if(d3) {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawFractal3DAntialiased(image_size);
                }
                else {
                    drawFractal3D(image_size);
                }
            }
            else if(bump_map || fake_de) {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawFractalAntialiasedPostProcess(image_size);
                }
                else {
                    drawFractalPostProcess(image_size);
                }
            }
            else {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawFractalAntialiased(image_size);
                }
                else {
                    drawFractal(image_size);
                }
            }
        }


        if(drawing_done != 0) {
            update(drawing_done);
        }

        int done = synchronization.incrementAndGet();

        total_calculated.addAndGet(thread_calculated);

        if(done == ptr.getNumberOfThreads()) {

            applyFilters();

            if(d3) {
                ptr.updateValues("3D mode");
            }
            else if(julia) {
                ptr.updateValues("Julia mode");
            }
            else {
                ptr.updateValues("Normal mode");
            }

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            if(d3) {
                ptr.getProgressBar().setValue((detail * detail) + (detail * detail / 100));
            }
            else {
                ptr.getProgressBar().setValue((image_size * image_size) + (image_size * image_size / 100));
            }

            int temp2 = image_size * image_size;
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double)total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }
    }

    private void drawPolar() {
        int image_size = image.getHeight();

        if(julia) {
            if(d3) {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawJuliaPolar3DAntialiased(image_size);
                }
                else {
                    drawJuliaPolar3D(image_size);
                }
            }
            else if(bump_map || fake_de) {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawJuliaPolarAntialiasedPostProcess(image_size);
                }
                else {
                    drawJuliaPolarPostProcess(image_size);
                }
            }
            else {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawJuliaPolarAntialiased(image_size);
                }
                else {
                    drawJuliaPolar(image_size);
                }
            }
        }
        else {
            if(d3) {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawFractalPolar3DAntialiased(image_size);
                }
                else {
                    drawFractalPolar3D(image_size);
                }
            }
            else if(bump_map || fake_de) {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawFractalPolarAntialiasedPostProcess(image_size);
                }
                else {
                    drawFractalPolarPostProcess(image_size);
                }
            }
            else {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawFractalPolarAntialiased(image_size);
                }
                else {
                    drawFractalPolar(image_size);
                }
            }
        }


        if(drawing_done != 0) {
            update(drawing_done);
        }

        int done = synchronization.incrementAndGet();

        total_calculated.addAndGet(thread_calculated);

        if(done == ptr.getNumberOfThreads()) {

            applyFilters();

            if(d3) {
                ptr.updateValues("3D mode");
            }
            else if(julia) {
                ptr.updateValues("Julia mode");
            }
            else {
                ptr.updateValues("Normal mode");
            }

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            if(d3) {
                ptr.getProgressBar().setValue((detail * detail) + (detail * detail / 100));
            }
            else {
                ptr.getProgressBar().setValue((image_size * image_size) + (image_size * image_size / 100));
            }

            int temp2 = image_size * image_size;
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double)total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }
    }

    /* private void AddQueue(int p) {
    
    try {
    if((Done[p] & Queued) > 0) {
    return;
    }
    }
    catch(Exception ex) {
    return;
    }
    
    Done[p] |= Queued;
    Queue[QueueHead++] = p;
    
    QueueHead %= QueueSize;
    
    }
    
    private void ScanFractal(int p, int image_size, int width, int height, double temp_size_image_size, double temp_xcenter_size, double temp_ycenter_size) {
    
    int x = p % width, y = p / width;
    int x2 = x + FROMx;
    int y2 = y + FROMy;
    
    double xreal;
    double yimag;
    
    int loc = y2 * image_size + x2;
    
    double center = LoadFractal(p, xreal = temp_xcenter_size + x2 * temp_size_image_size, yimag = temp_ycenter_size + y2 * temp_size_image_size, loc);
    boolean ll = x >= 1, rr = x < width - 1;
    boolean uu = y >= 1, dd = y < height - 1;
    
    int p1 = p - 1;
    int p2 = p + 1;
    int p3 = p - width;
    int p4 = p + width;
    
    boolean l = ll && LoadFractal(p1, xreal - temp_size_image_size, yimag, loc - 1) != center;
    boolean r = rr && LoadFractal(p2, xreal + temp_size_image_size, yimag, loc + 1) != center;
    boolean u = uu && LoadFractal(p3, xreal, yimag - temp_size_image_size, loc - image_size) != center;
    boolean d = dd && LoadFractal(p4, xreal, yimag + temp_size_image_size, loc + image_size) != center;
    
    if(l) AddQueue(p1);
    if(r) AddQueue(p2);
    if(u) AddQueue(p3);
    if(d) AddQueue(p4);
    
    // The corner pixels (nw,ne,sw,se) are also neighbors 
    if((uu&&ll)&&(l||u)) AddQueue(p3 - 1);
    if((uu&&rr)&&(r||u)) AddQueue(p3 + 1);
    if((dd&&ll)&&(l||d)) AddQueue(p4 - 1);
    if((dd&&rr)&&(r||d)) AddQueue(p4 + 1);
    
    }
    
    
    private double LoadFractal(int p, double xreal, double yimag, int loc) {  
    
    //int x = p % width + FROMx, y = p / width + FROMy;
    
    //int loc = y * image_size + x;
    
    if((Done[p] & Loaded) > 0) {
    return image_iterations[loc];
    }
    
    double result = image_iterations[loc] = fractal.calculateFractal(new Complex(xreal, yimag));
    
    //image.setRGB(loc % image.getHeight(), loc / image.getHeight(), result == max_iterations ? fractal_color.getRGB() : getPaletteColor(result + color_cycling_location).getRGB());//demo
    //ptr.getMainPanel().repaint();//demo
    
    rgbs[loc] = result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(result + color_cycling_location).getRGB();
    
    drawing_done++;
    
    Done[p] |= Loaded;
    
    return image_iterations[loc];
    
    }
    
     */
    private void drawFractalPostProcess(int image_size) {

        if(!boundary_tracing) {
            drawFractalBruteForcePostProcess(image_size);
        }
        else {
            drawFractalBoundaryTracingPostProcess(image_size);
        }

    }

    private void drawFractalBruteForcePostProcess(int image_size) {

        drawFractalBruteForce(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void drawFractalBoundaryTracingPostProcess(int image_size) {

        drawFractalBoundaryTracing(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void drawFractalAntialiasedPostProcess(int image_size) {

        if(!boundary_tracing) {
            drawFractalAntialiasedBruteForcePostProcess(image_size);
        }
        else {
            drawFractalAntialiasedBoundaryTracingPostProcess(image_size);
        }

    }

    private void drawFractalAntialiasedBoundaryTracingPostProcess(int image_size) {

        drawFractalAntialiasedBoundaryTracing(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void drawFractalPolarAntialiasedBoundaryTracingPostProcess(int image_size) {

        drawFractalPolarAntialiasedBoundaryTracing(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void drawJuliaPolarAntialiasedBoundaryTracingPostProcess(int image_size) {

        drawJuliaPolarAntialiasedBoundaryTracing(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void drawFractalAntialiasedBruteForcePostProcess(int image_size) {


        drawFractalAntialiasedBruteForce(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void drawJuliaPostProcess(int image_size) {

        if(!boundary_tracing) {
            drawJuliaBruteForcePostProcess(image_size);
        }
        else {
            drawJuliaBoundaryTracingPostProcess(image_size);
        }

    }

    private void drawJuliaBruteForcePostProcess(int image_size) {

        drawJuliaBruteForce(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);


    }

    private void drawJuliaBoundaryTracingPostProcess(int image_size) {

        drawJuliaBoundaryTracing(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void drawJuliaAntialiasedBoundaryTracingPostProcess(int image_size) {

        drawJuliaAntialiasedBoundaryTracing(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void drawJuliaAntialiasedBruteForcePostProcess(int image_size) {

        drawJuliaAntialiasedBruteForce(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void drawJuliaAntialiasedPostProcess(int image_size) {

        if(!boundary_tracing) {
            drawJuliaAntialiasedBruteForcePostProcess(image_size);
        }
        else {
            drawJuliaAntialiasedBoundaryTracingPostProcess(image_size);
        }

    }

    private void drawFractal(int image_size) {
        // ptr.setWholeImageDone(true); // demo

        if(!boundary_tracing) {
            drawFractalBruteForce(image_size);
        }
        else {
            drawFractalBoundaryTracing(image_size);
        }
    }

    private void drawFractalBruteForce(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;

        int pixel_percent = image_size * image_size / 100;

        //Better brute force
        double temp_result;

        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size + temp_size_image_size_y * y));
            rgbs[loc] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);


            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);


        /*int tile = 6;
        int image_size_tile = image_size / tile, tempx, tempy;
        condition = (image_size_tile) * (image_size_tile);
        
        int color, loc2;
        
        do {
        
        loc = normal_drawing_algorithm_pixel.getAndIncrement();
        
        if(loc >= condition) {
        break;
        }      
        
        
        tempx = loc % image_size_tile;
        tempy = loc / image_size_tile;
        
        x = tempx * tile;
        y = tempy * tile;
        
        loc2 = y * image_size + x;
        temp_result = image_iterations[loc2] = fractal.calculateFractal(new Complex(temp_xcenter_size + temp_size_image_size * x, temp_ycenter_size + temp_size_image_size * y));
        color = rgbs[loc2] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
        
        tempx = tempx == image_size_tile - 1 ? image_size : x + tile;
        tempy = tempy == image_size_tile - 1 ? image_size : y + tile;
        
        for(int i = y; i < tempy; i++) {
        for(int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
        rgbs[loc3] = color; 
        }
        }
        
        
        
        } while(true);*/

        thread_calculated += drawing_done;


        //Brute force
             /*double temp = temp_xcenter_size + temp_size_image_size * FROMx;
        double temp_y0 = temp_ycenter_size + temp_size_image_size * FROMy;
        double temp_x0 = temp;
        
        for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
        for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {
        
        temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
        rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
        
        
        drawing_done++;
        
        }
        
        if(drawing_done / pixel_percent >= 1) {
        update(drawing_done);
        thread_calculated += drawing_done;
        drawing_done = 0;
        }
        
        temp_x0 = temp;
        
        }
        
        thread_calculated += drawing_done;*/
    }

    private void drawFractalBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;

        int pixel_percent = image_size * image_size / 100;

        double temp_result;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;

        double temp_x0, temp_y0;
        //double delX[] = {temp_size_image_size, 0., -temp_size_image_size, 0.};
        //double delY[] = {0., temp_size_image_size, 0., -temp_size_image_size};


        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        //double curX, curY; 
        double nextX, nextY, start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};



        for(y = FROMy; y < TOy; y++) {
            temp_y0 = temp_ycenter_size + y * temp_size_image_size_y;
            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {
                    temp_x0 = temp_xcenter_size + x * temp_size_image_size_x;
                    //curX = temp_x0;
                    //curY = temp_y0;
                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations[pix] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                    startColor = rgbs[pix] = start_val == max_iterations ? fractal_color : palette_color.getPaletteColor(start_val + color_cycling_location);
                    drawing_done++;
                    thread_calculated++;
                    /*ptr.getMainPanel().repaint();
                    try {
                    Thread.sleep(1); //demo
                    }
                    catch (InterruptedException ex) {}*/

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                        curPix = startPix = startPix - image_size;
                        iy--;
                        //curY = temp_ycenter_size + iy * temp_size_image_size;
                    }

                    temp_ix = ix;
                    temp_iy = iy;

                    do {                                            // tracing cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            //nextX = curX + delX[dir]; 
                            //nextY = curY + delY[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];

                            nextX = temp_xcenter_size + next_ix * temp_size_image_size_x;
                            nextY = temp_ycenter_size + next_iy * temp_size_image_size_y;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                temp_result = image_iterations[nextPix] = fractal.calculateFractal(new Complex(nextX, nextY));
                                nextColor = rgbs[nextPix] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
                                drawing_done++;
                                thread_calculated++;
                                /*ptr.getMainPanel().repaint();
                                try {
                                Thread.sleep(1); //demo
                                }
                                catch (InterruptedException ex) {}*/
                            }


                            if(nextColor == startColor) {
                                curDir = dir;
                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                //curX = nextX;  
                                //curY = nextY;
                                break;
                            }
                        }
                    } while(curPix != startPix);


                    curDir = dirRight;


                    do {                                                 // 2nd cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];


                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if(rgbs[nextPix] == startColor) {           // flooding  
                                curDir = dir;
                                if(dir == dirUP) {
                                    floodPix = curPix;
                                    flood_ix = temp_ix;

                                    while(true) {
                                        flood_ix++;

                                        if(flood_ix >= TOx) {
                                            break;
                                        }

                                        floodPix++;

                                        if((floodColor = rgbs[floodPix]) == culcColor) {
                                            drawing_done++;
                                            rgbs[floodPix] = startColor;
                                            image_iterations[floodPix] = start_val;
                                            /*ptr.getMainPanel().repaint();
                                            try {
                                            Thread.sleep(1); //demo
                                            }
                                            catch (InterruptedException ex) {}*/
                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }

                                    if(drawing_done / pixel_percent >= 1) {
                                        update(drawing_done);
                                        drawing_done = 0;
                                    }
                                }

                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);

                }
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }
        }



        //Boundary Tracing Version 1
             /*
        int width = TOx - FROMx;
        int height = TOy - FROMy;
        
        for(int y = 0; y < width; y++) {
        AddQueue(y * width);
        AddQueue(y * width + (width - 1));
        }
        for(int x = 1; x < height - 1; x++) {
        AddQueue(x);
        AddQueue((height - 1) * width + x);
        }
        
        int flag=0;
        int counter = 0;
        int p;
        while(QueueTail != QueueHead) {
        if(QueueHead <= QueueTail || (++flag & 3) > 0) {
        p = Queue[QueueTail++];
        QueueTail %= QueueSize;
        } 
        else {
        p = Queue[--QueueHead];
        }
        
        ScanFractal(p, image_size, width, height, temp_size_image_size, temp_xcenter_size, temp_ycenter_size);
        
        // try {
        //   Thread.sleep(1); //demo
        //}
        //catch (InterruptedException ex) {}
        
        counter++;
        
        if(counter % height == 0 && drawing_done / pixel_percent >= 1) {
        update(drawing_done);
        thread_calculated += drawing_done;
        drawing_done = 0;
        }
        
        }
        
        thread_calculated += drawing_done;
        
        
        for(int y = 1; y < width - 1; y++) {
        for(int x = 1, loc = y * width + x, loc2 = (y + FROMy) * image_size + x + FROMx; x < height - 1; x++, loc++, loc2++) {
        //try {
        if(Done[loc + 1] == 0 && (Done[loc] & Loaded) > 0) {
        rgbs[loc2 + 1] = rgbs[loc2];
        image_iterations[loc2 + 1] = image_iterations[loc2];
        Done[loc + 1] |= Loaded; 
        drawing_done++;
        //image.setRGB(x + FROMx + 1, (y + FROMy), rgbs[loc2]);//demo
        //ptr.getMainPanel().repaint();//demo
        
        }
        //}
        //catch(Exception ex) {}
        }
        
        if(drawing_done / pixel_percent >= 1) {
        update(drawing_done);
        drawing_done = 0;
        }
        }*/

        //Solid Guessing algorithm

        /*int x = 0;
        int y = 0;
        boolean whole_area;
        int step;
        double temp_x0 = 0;
        double temp_y0;
        int total_drawing;
        
        double temp_starting_pixel_cicle;
        int temp_starting_pixel_color;
        
        int thread_size_width = TOx - FROMx;
        int thread_size_height = TOy - FROMy;              
        
        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;
        double temp_result;
        int loc;
        
        for(int i = 0; i < thread_slices; i++) {
        slice_FROMy = FROMy + i * thread_size_height / thread_slices;
        slice_TOy = FROMy + (i + 1) * (thread_size_height) / thread_slices;
        for(int j = 0; j < thread_slices; j++) {
        slice_FROMx =  FROMx + j * (thread_size_width) / thread_slices;
        slice_TOx = FROMx + (j + 1) * (thread_size_width) / thread_slices;  
        
        double temp = (slice_TOy - slice_FROMy + 1) / 2;
        
        for(y = slice_FROMy, whole_area = true, step = 0, total_drawing = 0; step < temp; step++, whole_area = true) {
        
        temp_y0 = temp_ycenter_size + temp_size_image_size * y;           
        
        x = slice_FROMx + step;
        temp_x0 = temp_xcenter_size + temp_size_image_size * x;                        
        
        loc = y * image_size + x;
        
        temp_starting_pixel_cicle =  temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
        
        rgbs[loc] = temp_starting_pixel_color = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
        
        image.setRGB(x, y, rgbs[loc]);//demo
        ptr.getMainPanel().repaint();//demo
        try {
        Thread.sleep(1); //demo
        }
        catch (InterruptedException ex) {}
        
        drawing_done++;
        total_drawing++;
        
        for(x++, loc++; x < slice_TOx - step; x++, loc++) {
        temp_x0 += temp_size_image_size;
        
        temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
        
        if(temp_result == temp_starting_pixel_cicle) {
        rgbs[loc] = temp_starting_pixel_color;
        image.setRGB(x, y, rgbs[loc]);//demo
        ptr.getMainPanel().repaint();//demo
        }
        else {
        rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
        image.setRGB(x, y, rgbs[loc]);//demo
        ptr.getMainPanel().repaint();//demo
        whole_area = false;
        }
        
        try {
        Thread.sleep(1); //demo
        }
        catch (InterruptedException ex) {}
        
        
        drawing_done++;
        total_drawing++;
        
        }
        
        for(x--, y++; y < slice_TOy - step; y++) {
        temp_y0 += temp_size_image_size;//= temp_ycenter_size + temp_size_image_size * y;
        loc = y * image_size + x;
        temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
        
        
        if(temp_result == temp_starting_pixel_cicle) {
        rgbs[loc] = temp_starting_pixel_color;
        image.setRGB(x, y, rgbs[loc]);//demo
        ptr.getMainPanel().repaint();//demo
        }
        else {
        rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
        image.setRGB(x, y, rgbs[loc]);//demo
        ptr.getMainPanel().repaint();//demo
        whole_area = false;
        }
        
        try {
        Thread.sleep(1); //demo
        }
        catch (InterruptedException ex) {}
        
        drawing_done++;
        total_drawing++;
        }
        
        for(y--, x--, loc = y * image_size + x; x >= slice_FROMx + step; x--, loc--) {
        temp_x0 -= temp_size_image_size;//= temp_xcenter_size + temp_size_image_size * x;
        temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
        
        
        if(temp_result == temp_starting_pixel_cicle) {
        rgbs[loc] = temp_starting_pixel_color;
        image.setRGB(x, y, rgbs[loc]);//demo
        ptr.getMainPanel().repaint();//demo
        }
        else {
        rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
        image.setRGB(x, y, rgbs[loc]);//demo
        ptr.getMainPanel().repaint();//demo
        whole_area = false;
        }
        
        try {
        Thread.sleep(1); //demo
        }
        catch (InterruptedException ex) {}
        
        drawing_done++;
        total_drawing++;
        }
        
        for(x++, y--; y > slice_FROMy + step; y--) {
        temp_y0 -= temp_size_image_size;//= temp_ycenter_size + temp_size_image_size * y;
        loc = y * image_size + x;
        temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
        
        
        if(temp_result == temp_starting_pixel_cicle) {
        rgbs[loc] = temp_starting_pixel_color;
        image.setRGB(x, y, rgbs[loc]);//demo
        ptr.getMainPanel().repaint();//demo
        }
        else {
        rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
        image.setRGB(x, y, rgbs[loc]);//demo
        ptr.getMainPanel().repaint();//demo
        whole_area = false;
        }
        
        drawing_done++;
        total_drawing++;
        }
        y++;
        x++;
        
        
        if(drawing_done / pixel_percent >= 1) {
        update(drawing_done);
        thread_calculated += drawing_done;
        drawing_done = 0;
        }
        
        
        
        if(whole_area) {
        int temp6 = step + 1;
        int temp1 = slice_TOx - temp6;
        int temp2 = slice_TOy - temp6;
        
        int temp3;
        int temp4; 
        for(int k = y; k < temp2; k++) {
        temp3 = k * image_size + x;
        temp4 = k * image_size + temp1;
        Arrays.fill(image_iterations, temp3, temp4, temp_starting_pixel_cicle);
        Arrays.fill(rgbs, temp3, temp4, temp_starting_pixel_color);
        }
        
        update((slice_TOx - slice_FROMx) * (slice_TOy - slice_FROMy) - total_drawing);
        break;
        }
        }
        }
        }
        
        thread_calculated += drawing_done; */
    }

    private void drawFractalPolar(int image_size) {

        if(!boundary_tracing) {
            drawFractalPolarBruteForce(image_size);
        }
        else {
            drawFractalPolarBoundaryTracing(image_size);
        }

    }

    private void drawFractalPolarAntialiased(int image_size) {

        if(!boundary_tracing) {
            drawFractalPolarAntialiasedBruteForce(image_size);
        }
        else {
            drawFractalPolarAntialiasedBoundaryTracing(image_size);
        }

    }

    private void drawJuliaPolarAntialiased(int image_size) {

        if(!boundary_tracing) {
            drawJuliaPolarAntialiasedBruteForce(image_size);
        }
        else {
            drawJuliaPolarAntialiasedBoundaryTracing(image_size);
        }

    }

    private void drawJuliaPolar(int image_size) {

        if(!boundary_tracing) {
            drawJuliaPolarBruteForce(image_size);
        }
        else {
            drawJuliaPolarBoundaryTracing(image_size);
        }

    }

    private void drawFractalPolarPostProcess(int image_size) {

        if(!boundary_tracing) {
            drawFractalPolarBruteForcePostProcess(image_size);
        }
        else {
            drawFractalPolarBoundaryTracingPostProcess(image_size);
        }

    }

    private void drawFractalPolarAntialiasedPostProcess(int image_size) {

        if(!boundary_tracing) {
            drawFractalPolarAntialiasedBruteForcePostProcess(image_size);
        }
        else {
            drawFractalPolarAntialiasedBoundaryTracingPostProcess(image_size);
        }

    }

    private void drawJuliaPolarAntialiasedPostProcess(int image_size) {

        if(!boundary_tracing) {
            drawJuliaPolarAntialiasedBruteForcePostProcess(image_size);
        }
        else {
            drawJuliaPolarAntialiasedBoundaryTracingPostProcess(image_size);
        }

    }

    private void drawJuliaPolarPostProcess(int image_size) {

        if(!boundary_tracing) {
            drawJuliaPolarBruteForcePostProcess(image_size);
        }
        else {
            drawJuliaPolarBoundaryTracingPostProcess(image_size);
        }

    }

    private void drawFractalPolarBruteForcePostProcess(int image_size) {

        drawFractalPolarBruteForce(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void drawFractalPolarBoundaryTracingPostProcess(int image_size) {

        drawFractalPolarBoundaryTracing(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void drawJuliaPolarBruteForcePostProcess(int image_size) {

        drawJuliaPolarBruteForce(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void drawJuliaPolarBoundaryTracingPostProcess(int image_size) {

        drawJuliaPolarBoundaryTracing(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void drawFractalPolarAntialiasedBruteForcePostProcess(int image_size) {

        drawFractalPolarAntialiasedBruteForce(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void drawJuliaPolarAntialiasedBruteForcePostProcess(int image_size) {

        drawJuliaPolarAntialiasedBruteForce(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void drawFractalPolarBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, r2, f2, sf2, cf2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int pixel_percent = image_size * image_size / 100;

        double temp_result;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;

        double temp_x0, temp_y0;
        //double delX[] = {temp_size_image_size, 0., -temp_size_image_size, 0.};
        //double delY[] = {0., temp_size_image_size, 0., -temp_size_image_size};


        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        //double curX, curY; 
        double nextX, nextY, start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};



        for(y = FROMy; y < TOy; y++) {

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {

                    r = Math.exp(x * mulx + start);

                    temp_x0 = xcenter + r * cf;
                    temp_y0 = ycenter + r * sf;
                    //curX = temp_x0;
                    //curY = temp_y0;
                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations[pix] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                    startColor = rgbs[pix] = start_val == max_iterations ? fractal_color : palette_color.getPaletteColor(start_val + color_cycling_location);
                    drawing_done++;
                    thread_calculated++;
                    /*ptr.getMainPanel().repaint();
                    try {
                    Thread.sleep(1); //demo
                    }
                    catch (InterruptedException ex) {}*/

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                        curPix = startPix = startPix - image_size;
                        iy--;
                        //curY = temp_ycenter_size + iy * temp_size_image_size;
                    }

                    temp_ix = ix;
                    temp_iy = iy;

                    do {                                            // tracing cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            //nextX = curX + delX[dir]; 
                            //nextY = curY + delY[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];

                            f2 = next_iy * muly;
                            sf2 = Math.sin(f2);
                            cf2 = Math.cos(f2);

                            r2 = Math.exp(next_ix * mulx + start);

                            nextX = xcenter + r2 * cf2;
                            nextY = ycenter + r2 * sf2;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                temp_result = image_iterations[nextPix] = fractal.calculateFractal(new Complex(nextX, nextY));
                                nextColor = rgbs[nextPix] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
                                drawing_done++;
                                thread_calculated++;
                                /*ptr.getMainPanel().repaint();
                                try {
                                Thread.sleep(1); //demo
                                }
                                catch (InterruptedException ex) {}*/
                            }


                            if(nextColor == startColor) {
                                curDir = dir;
                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                //curX = nextX;  
                                //curY = nextY;
                                break;
                            }
                        }
                    } while(curPix != startPix);


                    curDir = dirRight;


                    do {                                                 // 2nd cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];


                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if(rgbs[nextPix] == startColor) {           // flooding  
                                curDir = dir;
                                if(dir == dirUP) {
                                    floodPix = curPix;
                                    flood_ix = temp_ix;

                                    while(true) {
                                        flood_ix++;

                                        if(flood_ix >= TOx) {
                                            break;
                                        }

                                        floodPix++;

                                        if((floodColor = rgbs[floodPix]) == culcColor) {
                                            drawing_done++;
                                            rgbs[floodPix] = startColor;
                                            image_iterations[floodPix] = start_val;
                                            /*ptr.getMainPanel().repaint();
                                            try {
                                            Thread.sleep(1); //demo
                                            }
                                            catch (InterruptedException ex) {}*/
                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }

                                    if(drawing_done / pixel_percent >= 1) {
                                        update(drawing_done);
                                        drawing_done = 0;
                                    }
                                }

                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);

                }
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }
        }
    }

    private void drawFractalPolarAntialiasedBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, r2, f2, sf2, cf2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int pixel_percent = image_size * image_size / 100;

        double temp_result;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;

        double temp_x0, temp_y0;


        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};

        double nextX, nextY, start_val;

        double sf3, cf3, r3;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};


        double exp_x_antialiasing_size = Math.exp(x_antialiasing_size);
        double exp_inv_x_antialiasing_size = 1 / exp_x_antialiasing_size;

        double exp_x_antialiasing_size_x2 = exp_x_antialiasing_size * exp_x_antialiasing_size;
        double exp_inv_x_antialiasing_size_x2 = 1 / exp_x_antialiasing_size_x2;

        double antialiasing_x[] = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
            exp_inv_x_antialiasing_size, exp_x_antialiasing_size, 1, 1,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, 1, 1, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};


        double sin_y_antialiasing_size = Math.sin(y_antialiasing_size);
        double cos_y_antialiasing_size = Math.cos(y_antialiasing_size);

        double sin_inv_y_antialiasing_size = -sin_y_antialiasing_size;
        double cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        double sin_y_antialiasing_size_x2 = 2 * sin_y_antialiasing_size * cos_y_antialiasing_size;
        double cos_y_antialiasing_size_x2 = 2 * cos_y_antialiasing_size * cos_y_antialiasing_size - 1;

        double sin_inv_y_antialiasing_size_x2 = -sin_y_antialiasing_size_x2;
        double cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;


        double antialiasing_y_sin[] = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
            0, 0, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
            sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2,
            sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};


        double antialiasing_y_cos[] = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
            1, 1, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
            cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2,
            cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};


        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;

        int red, green, blue, color;

        for(y = FROMy; y < TOy; y++) {

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {

                    r = Math.exp(x * mulx + start);

                    temp_x0 = xcenter + r * cf;
                    temp_y0 = ycenter + r * sf;

                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations[pix] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                    color = start_val == max_iterations ? fractal_color : palette_color.getPaletteColor(start_val + color_cycling_location);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {

                        sf3 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                        cf3 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                        r3 = r * antialiasing_x[i];

                        temp_x0 = xcenter + r3 * cf3;
                        temp_y0 = ycenter + r3 * sf3;

                        temp_result = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                        color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    startColor = rgbs[pix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));


                    drawing_done++;
                    thread_calculated++;


                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                        curPix = startPix = startPix - image_size;
                        iy--;
                    }

                    temp_ix = ix;
                    temp_iy = iy;

                    do {                                            // tracing cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];

                            f2 = next_iy * muly;
                            sf2 = Math.sin(f2);
                            cf2 = Math.cos(f2);

                            r2 = Math.exp(next_ix * mulx + start);

                            nextX = xcenter + r2 * cf2;
                            nextY = ycenter + r2 * sf2;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                temp_result = image_iterations[nextPix] = fractal.calculateFractal(new Complex(nextX, nextY));
                                color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {

                                    sf3 = sf2 * antialiasing_y_cos[i] + cf2 * antialiasing_y_sin[i];
                                    cf3 = cf2 * antialiasing_y_cos[i] - sf2 * antialiasing_y_sin[i];

                                    r3 = r2 * antialiasing_x[i];

                                    nextX = xcenter + r3 * cf3;
                                    nextY = ycenter + r3 * sf3;

                                    temp_result = fractal.calculateFractal(new Complex(nextX, nextY));
                                    color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                nextColor = rgbs[nextPix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                                drawing_done++;
                                thread_calculated++;

                            }


                            if(nextColor == startColor) {
                                curDir = dir;
                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);


                    curDir = dirRight;


                    do {                                                 // 2nd cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];


                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if(rgbs[nextPix] == startColor) {           // flooding  
                                curDir = dir;
                                if(dir == dirUP) {
                                    floodPix = curPix;
                                    flood_ix = temp_ix;

                                    while(true) {
                                        flood_ix++;

                                        if(flood_ix >= TOx) {
                                            break;
                                        }

                                        floodPix++;

                                        if((floodColor = rgbs[floodPix]) == culcColor) {
                                            drawing_done++;
                                            rgbs[floodPix] = startColor;
                                            image_iterations[floodPix] = start_val;
                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }

                                    if(drawing_done / pixel_percent >= 1) {
                                        update(drawing_done);
                                        drawing_done = 0;
                                    }
                                }

                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);

                }
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }
        }
    }

    private void drawJuliaPolarAntialiasedBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, r2, f2, sf2, cf2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int pixel_percent = image_size * image_size / 100;

        double temp_result;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;

        double temp_x0, temp_y0;


        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};

        double nextX, nextY, start_val;

        double sf3, cf3, r3;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};


        double exp_x_antialiasing_size = Math.exp(x_antialiasing_size);
        double exp_inv_x_antialiasing_size = 1 / exp_x_antialiasing_size;

        double exp_x_antialiasing_size_x2 = exp_x_antialiasing_size * exp_x_antialiasing_size;
        double exp_inv_x_antialiasing_size_x2 = 1 / exp_x_antialiasing_size_x2;

        double antialiasing_x[] = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
            exp_inv_x_antialiasing_size, exp_x_antialiasing_size, 1, 1,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, 1, 1, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};


        double sin_y_antialiasing_size = Math.sin(y_antialiasing_size);
        double cos_y_antialiasing_size = Math.cos(y_antialiasing_size);

        double sin_inv_y_antialiasing_size = -sin_y_antialiasing_size;
        double cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        double sin_y_antialiasing_size_x2 = 2 * sin_y_antialiasing_size * cos_y_antialiasing_size;
        double cos_y_antialiasing_size_x2 = 2 * cos_y_antialiasing_size * cos_y_antialiasing_size - 1;

        double sin_inv_y_antialiasing_size_x2 = -sin_y_antialiasing_size_x2;
        double cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;


        double antialiasing_y_sin[] = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
            0, 0, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
            sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2,
            sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};


        double antialiasing_y_cos[] = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
            1, 1, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
            cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2,
            cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};


        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;

        int red, green, blue, color;

        for(y = FROMy; y < TOy; y++) {

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {

                    r = Math.exp(x * mulx + start);

                    temp_x0 = xcenter + r * cf;
                    temp_y0 = ycenter + r * sf;

                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                    color = start_val == max_iterations ? fractal_color : palette_color.getPaletteColor(start_val + color_cycling_location);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {

                        sf3 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                        cf3 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                        r3 = r * antialiasing_x[i];

                        temp_x0 = xcenter + r3 * cf3;
                        temp_y0 = ycenter + r3 * sf3;

                        temp_result = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                        color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    startColor = rgbs[pix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));


                    drawing_done++;
                    thread_calculated++;


                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                        curPix = startPix = startPix - image_size;
                        iy--;
                    }

                    temp_ix = ix;
                    temp_iy = iy;

                    do {                                            // tracing cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];

                            f2 = next_iy * muly;
                            sf2 = Math.sin(f2);
                            cf2 = Math.cos(f2);

                            r2 = Math.exp(next_ix * mulx + start);

                            nextX = xcenter + r2 * cf2;
                            nextY = ycenter + r2 * sf2;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                temp_result = image_iterations[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {

                                    sf3 = sf2 * antialiasing_y_cos[i] + cf2 * antialiasing_y_sin[i];
                                    cf3 = cf2 * antialiasing_y_cos[i] - sf2 * antialiasing_y_sin[i];

                                    r3 = r2 * antialiasing_x[i];

                                    nextX = xcenter + r3 * cf3;
                                    nextY = ycenter + r3 * sf3;

                                    temp_result = fractal.calculateJulia(new Complex(nextX, nextY));
                                    color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                nextColor = rgbs[nextPix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                                drawing_done++;
                                thread_calculated++;

                            }


                            if(nextColor == startColor) {
                                curDir = dir;
                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);


                    curDir = dirRight;


                    do {                                                 // 2nd cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];


                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if(rgbs[nextPix] == startColor) {           // flooding  
                                curDir = dir;
                                if(dir == dirUP) {
                                    floodPix = curPix;
                                    flood_ix = temp_ix;

                                    while(true) {
                                        flood_ix++;

                                        if(flood_ix >= TOx) {
                                            break;
                                        }

                                        floodPix++;

                                        if((floodColor = rgbs[floodPix]) == culcColor) {
                                            drawing_done++;
                                            rgbs[floodPix] = startColor;
                                            image_iterations[floodPix] = start_val;
                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }

                                    if(drawing_done / pixel_percent >= 1) {
                                        update(drawing_done);
                                        drawing_done = 0;
                                    }
                                }

                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);

                }
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }
        }
    }

    private void drawJuliaPolarBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, r2, f2, sf2, cf2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int pixel_percent = image_size * image_size / 100;

        double temp_result;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};

        double nextX, nextY, start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};



        for(y = FROMy; y < TOy; y++) {

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {

                    r = Math.exp(x * mulx + start);

                    temp_x0 = xcenter + r * cf;
                    temp_y0 = ycenter + r * sf;

                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                    startColor = rgbs[pix] = start_val == max_iterations ? fractal_color : palette_color.getPaletteColor(start_val + color_cycling_location);
                    drawing_done++;
                    thread_calculated++;


                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                        curPix = startPix = startPix - image_size;
                        iy--;
                    }

                    temp_ix = ix;
                    temp_iy = iy;

                    do {                                            // tracing cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];

                            f2 = next_iy * muly;
                            sf2 = Math.sin(f2);
                            cf2 = Math.cos(f2);

                            r2 = Math.exp(next_ix * mulx + start);

                            nextX = xcenter + r2 * cf2;
                            nextY = ycenter + r2 * sf2;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                temp_result = image_iterations[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                nextColor = rgbs[nextPix] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
                                drawing_done++;
                                thread_calculated++;

                            }


                            if(nextColor == startColor) {
                                curDir = dir;
                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;

                                break;
                            }
                        }
                    } while(curPix != startPix);


                    curDir = dirRight;


                    do {                                                 // 2nd cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];


                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if(rgbs[nextPix] == startColor) {           // flooding  
                                curDir = dir;
                                if(dir == dirUP) {
                                    floodPix = curPix;
                                    flood_ix = temp_ix;

                                    while(true) {
                                        flood_ix++;

                                        if(flood_ix >= TOx) {
                                            break;
                                        }

                                        floodPix++;

                                        if((floodColor = rgbs[floodPix]) == culcColor) {
                                            drawing_done++;
                                            rgbs[floodPix] = startColor;
                                            image_iterations[floodPix] = start_val;

                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }

                                    if(drawing_done / pixel_percent >= 1) {
                                        update(drawing_done);
                                        drawing_done = 0;
                                    }
                                }

                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);

                }
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }
        }
    }

    public void drawFractalPolarBruteForce(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        /*double start;
        double end = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = -mulx * image_size + end;*/
        
        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        double temp_result;

        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(xcenter + r * cf, ycenter + r * sf));
            rgbs[loc] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);


            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

    }

    private void drawFractalPolarAntialiasedBruteForce(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        double temp_result;

        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        int color;

        int red, green, blue;

        double exp_x_antialiasing_size = Math.exp(x_antialiasing_size);
        double exp_inv_x_antialiasing_size = 1 / exp_x_antialiasing_size;

        double exp_x_antialiasing_size_x2 = exp_x_antialiasing_size * exp_x_antialiasing_size;
        double exp_inv_x_antialiasing_size_x2 = 1 / exp_x_antialiasing_size_x2;

        double antialiasing_x[] = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
            exp_inv_x_antialiasing_size, exp_x_antialiasing_size, 1, 1,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, 1, 1, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};


        double sin_y_antialiasing_size = Math.sin(y_antialiasing_size);
        double cos_y_antialiasing_size = Math.cos(y_antialiasing_size);

        double sin_inv_y_antialiasing_size = -sin_y_antialiasing_size;
        double cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        double sin_y_antialiasing_size_x2 = 2 * sin_y_antialiasing_size * cos_y_antialiasing_size;
        double cos_y_antialiasing_size_x2 = 2 * cos_y_antialiasing_size * cos_y_antialiasing_size - 1;

        double sin_inv_y_antialiasing_size_x2 = -sin_y_antialiasing_size_x2;
        double cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;


        double antialiasing_y_sin[] = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
            0, 0, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
            sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2,
            sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};


        double antialiasing_y_cos[] = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
            1, 1, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
            cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2,
            cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};

        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(xcenter + r * cf, ycenter + r * sf));
            color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {

                sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                r2 = r * antialiasing_x[i];

                temp_result = fractal.calculateFractal(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));


            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

    }

    private void drawJuliaPolarAntialiasedBruteForce(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        double temp_result;

        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        int color;

        int red, green, blue;

        double exp_x_antialiasing_size = Math.exp(x_antialiasing_size);
        double exp_inv_x_antialiasing_size = 1 / exp_x_antialiasing_size;

        double exp_x_antialiasing_size_x2 = exp_x_antialiasing_size * exp_x_antialiasing_size;
        double exp_inv_x_antialiasing_size_x2 = 1 / exp_x_antialiasing_size_x2;

        double antialiasing_x[] = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
            exp_inv_x_antialiasing_size, exp_x_antialiasing_size, 1, 1,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, 1, 1, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};


        double sin_y_antialiasing_size = Math.sin(y_antialiasing_size);
        double cos_y_antialiasing_size = Math.cos(y_antialiasing_size);

        double sin_inv_y_antialiasing_size = -sin_y_antialiasing_size;
        double cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        double sin_y_antialiasing_size_x2 = 2 * sin_y_antialiasing_size * cos_y_antialiasing_size;
        double cos_y_antialiasing_size_x2 = 2 * cos_y_antialiasing_size * cos_y_antialiasing_size - 1;

        double sin_inv_y_antialiasing_size_x2 = -sin_y_antialiasing_size_x2;
        double cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;


        double antialiasing_y_sin[] = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
            0, 0, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
            sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2,
            sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};


        double antialiasing_y_cos[] = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
            1, 1, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
            cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2,
            cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};

        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(xcenter + r * cf, ycenter + r * sf));
            color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {

                sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                r2 = r * antialiasing_x[i];

                temp_result = fractal.calculateJulia(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));


            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

    }

    private void drawJuliaPolarBruteForce(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        double temp_result;

        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(xcenter + r * cf, ycenter + r * sf));
            rgbs[loc] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);


            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

    }

    public void rotate(int image_size, boolean update_progress) {

        int x, y;

        int w2 = image_size / 2;

        int n1 = detail - 1;

        double ct = Math.cos(fiX), cf = Math.cos(fiY), st = Math.sin(fiX), sf = Math.sin(fiY);
        double m00 = scale * cf, m02 = scale * sf, m10 = scale * st * sf, m11 = scale * ct, m12 = -scale * st * cf;
        m20 = -ct * sf;
        m21 = st;
        m22 = ct * cf;

        int pixel_percent = detail * detail / 100;

        for(x = FROMx; x < TOx; x++) {
            for(y = FROMy; y < TOy; y++) {
                vert1[x][y][0] = (float)(m00 * vert[x][y][0] + m02 * vert[x][y][2]);
                vert1[x][y][1] = (float)(m10 * vert[x][y][0] + m11 * vert[x][y][1] + m12 * vert[x][y][2]);

                if(y < n1 && x < n1) {
                    Norm1z[x][y][0] = (float)(m20 * Norm[x][y][0][0] + m21 * Norm[x][y][0][1] + m22 * Norm[x][y][0][2]);
                    Norm1z[x][y][1] = (float)(m20 * Norm[x][y][1][0] + m21 * Norm[x][y][1][1] + m22 * Norm[x][y][1][2]);
                }
                
                if(update_progress) {
                    drawing_done++;
                }
            }

            if(update_progress && (drawing_done / pixel_percent >= 1)) {
                update(drawing_done);
                drawing_done = 0;
            }
        }


        int sync2 = synchronization2.incrementAndGet();

        if(sync2 == ptr.getNumberOfThreads()) {

            int[] xPol = new int[3];
            int[] yPol = new int[3];

            Graphics2D g = image.createGraphics();

            int ib = 0, ie = n1, sti = 1, jb = 0, je = n1, stj = 1;

            if(m20 < 0) {
                ib = n1;
                ie = -1;
                sti = -1;
            }

            if(m22 < 0) {
                jb = n1;
                je = -1;
                stj = -1;
            }

            
            double second_color = 1 - color_3d_blending;

            for(int i = ib; i != ie; i += sti) {
                for(int j = jb; j != je; j += stj) {


                    double red = ((((int)vert[i][j][3]) >> 16) & 0xff) * color_3d_blending;
                    double green = ((((int)vert[i][j][3]) >> 8) & 0xff) * color_3d_blending;
                    double blue = (((int)vert[i][j][3]) & 0xff) * color_3d_blending;

                    if(Norm1z[i][j][0] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i + 1][j][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i + 1][j][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/

                    }

                    if(Norm1z[i][j][1] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i][j + 1][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i][j + 1][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/

                    }
                }
            }
        }

    }

    private void drawFractal3D(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;


        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;
        double mod;
        double dx = image_size / (double)n1, dr_x = size / n1, dr_y = (size * height_ratio) / n1;


        int x, y, loc, counter = 0;

        int condition = detail * detail;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            vert[x][y][0] = (float)(dx * x - w2);
            vert[x][y][2] = (float)(dx * y - w2);
            temp = fractal.calculateFractal3D(new Complex(temp_xcenter_size + dr_x * x, temp_ycenter_size + dr_y * y));
            vert[x][y][1] = (float)(temp[0] * d3_height_scale + d3_height_offset);
            vert[x][y][3] = temp[1] == max_iterations ? fractal_color : palette_color.getPaletteColor(temp[1] + color_cycling_location);

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }


        } while(true);


        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        double ct = Math.cos(fiX), cf = Math.cos(fiY), st = Math.sin(fiX), sf = Math.sin(fiY);
        double m00 = scale * cf, m02 = scale * sf, m10 = scale * st * sf, m11 = scale * ct, m12 = -scale * st * cf;
        m20 = -ct * sf;
        m21 = st;
        m22 = ct * cf;

        for(x = FROMx; x < TOx; x++) {
            for(y = FROMy; y < TOy; y++) {
                if(x < n1 && y < n1) {
                    Norm[x][y][0][0] = vert[x][y][1] - vert[x + 1][y][1];
                    Norm[x][y][0][1] = (float)(dx);
                    Norm[x][y][0][2] = vert[x + 1][y][1] - vert[x + 1][y + 1][1];
                    mod = Math.sqrt(Norm[x][y][0][0] * Norm[x][y][0][0] + Norm[x][y][0][1] * Norm[x][y][0][1] + Norm[x][y][0][2] * Norm[x][y][0][2]) / 255.5;
                    Norm[x][y][0][0] /= mod;
                    Norm[x][y][0][1] /= mod;
                    Norm[x][y][0][2] /= mod;
                    Norm[x][y][1][0] = vert[x][y + 1][1] - vert[x + 1][y + 1][1];
                    Norm[x][y][1][1] = (float)(dx);
                    Norm[x][y][1][2] = vert[x][y][1] - vert[x][y + 1][1];
                    mod = Math.sqrt(Norm[x][y][1][0] * Norm[x][y][1][0] + Norm[x][y][1][1] * Norm[x][y][1][1] + Norm[x][y][1][2] * Norm[x][y][1][2]) / 255.5;
                    Norm[x][y][1][0] /= mod;
                    Norm[x][y][1][1] /= mod;
                    Norm[x][y][1][2] /= mod;

                    Norm1z[x][y][0] = (float)(m20 * Norm[x][y][0][0] + m21 * Norm[x][y][0][1] + m22 * Norm[x][y][0][2]);
                    Norm1z[x][y][1] = (float)(m20 * Norm[x][y][1][0] + m21 * Norm[x][y][1][1] + m22 * Norm[x][y][1][2]);
                }
                vert1[x][y][0] = (float)(m00 * vert[x][y][0] + m02 * vert[x][y][2]);
                vert1[x][y][1] = (float)(m10 * vert[x][y][0] + m11 * vert[x][y][1] + m12 * vert[x][y][2]);
            }
        }

        int sync3 = synchronization3.incrementAndGet();

        if(sync3 == ptr.getNumberOfThreads()) {

            int[] xPol = new int[3];
            int[] yPol = new int[3];

            Graphics2D g = image.createGraphics();

            int ib = 0, ie = n1, sti = 1, jb = 0, je = n1, stj = 1;

            if(m20 < 0) {
                ib = n1;
                ie = -1;
                sti = -1;
            }

            if(m22 < 0) {
                jb = n1;
                je = -1;
                stj = -1;
            }

            
            double second_color = 1 - color_3d_blending;

            for(int i = ib; i != ie; i += sti) {
                for(int j = jb; j != je; j += stj) {


                    double red = ((((int)vert[i][j][3]) >> 16) & 0xff) * color_3d_blending;
                    double green = ((((int)vert[i][j][3]) >> 8) & 0xff) * color_3d_blending;
                    double blue = (((int)vert[i][j][3]) & 0xff) * color_3d_blending;

                    if(Norm1z[i][j][0] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i + 1][j][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i + 1][j][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/
                    }

                    if(Norm1z[i][j][1] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i][j + 1][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i][j + 1][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/

                    }
                }
            }

            thread_calculated = image_size * image_size;
        }

    }

    private void drawFractalPolar3D(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;
        double mod;
        double dx = image_size / (double)n1;

        double start;
        double center = Math.log(size);

        double f2, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / n1;

        double mulx = muly * height_ratio;

        start = center - mulx * n1 / 2.0;


        int x, y, loc, counter = 0;

        int condition = detail * detail;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            f2 = y * muly;
            sf2 = Math.sin(f2);
            cf2 = Math.cos(f2);

            r2 = Math.exp(x * mulx + start);

            vert[x][y][0] = (float)(dx * x - w2);
            vert[x][y][2] = (float)(dx * y - w2);
            temp = fractal.calculateFractal3D(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
            vert[x][y][1] = (float)(temp[0] * d3_height_scale + d3_height_offset);
            vert[x][y][3] = temp[1] == max_iterations ? fractal_color : palette_color.getPaletteColor(temp[1] + color_cycling_location);

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }


        } while(true);


        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        double ct = Math.cos(fiX), cf = Math.cos(fiY), st = Math.sin(fiX), sf = Math.sin(fiY);
        double m00 = scale * cf, m02 = scale * sf, m10 = scale * st * sf, m11 = scale * ct, m12 = -scale * st * cf;
        m20 = -ct * sf;
        m21 = st;
        m22 = ct * cf;

        for(x = FROMx; x < TOx; x++) {
            for(y = FROMy; y < TOy; y++) {
                if(x < n1 && y < n1) {
                    Norm[x][y][0][0] = vert[x][y][1] - vert[x + 1][y][1];
                    Norm[x][y][0][1] = (float)(dx);
                    Norm[x][y][0][2] = vert[x + 1][y][1] - vert[x + 1][y + 1][1];
                    mod = Math.sqrt(Norm[x][y][0][0] * Norm[x][y][0][0] + Norm[x][y][0][1] * Norm[x][y][0][1] + Norm[x][y][0][2] * Norm[x][y][0][2]) / 255.5;
                    Norm[x][y][0][0] /= mod;
                    Norm[x][y][0][1] /= mod;
                    Norm[x][y][0][2] /= mod;
                    Norm[x][y][1][0] = vert[x][y + 1][1] - vert[x + 1][y + 1][1];
                    Norm[x][y][1][1] = (float)(dx);
                    Norm[x][y][1][2] = vert[x][y][1] - vert[x][y + 1][1];
                    mod = Math.sqrt(Norm[x][y][1][0] * Norm[x][y][1][0] + Norm[x][y][1][1] * Norm[x][y][1][1] + Norm[x][y][1][2] * Norm[x][y][1][2]) / 255.5;
                    Norm[x][y][1][0] /= mod;
                    Norm[x][y][1][1] /= mod;
                    Norm[x][y][1][2] /= mod;

                    Norm1z[x][y][0] = (float)(m20 * Norm[x][y][0][0] + m21 * Norm[x][y][0][1] + m22 * Norm[x][y][0][2]);
                    Norm1z[x][y][1] = (float)(m20 * Norm[x][y][1][0] + m21 * Norm[x][y][1][1] + m22 * Norm[x][y][1][2]);
                }
                vert1[x][y][0] = (float)(m00 * vert[x][y][0] + m02 * vert[x][y][2]);
                vert1[x][y][1] = (float)(m10 * vert[x][y][0] + m11 * vert[x][y][1] + m12 * vert[x][y][2]);
            }
        }

        int sync3 = synchronization3.incrementAndGet();

        if(sync3 == ptr.getNumberOfThreads()) {

            int[] xPol = new int[3];
            int[] yPol = new int[3];

            Graphics2D g = image.createGraphics();

            int ib = 0, ie = n1, sti = 1, jb = 0, je = n1, stj = 1;

            if(m20 < 0) {
                ib = n1;
                ie = -1;
                sti = -1;
            }

            if(m22 < 0) {
                jb = n1;
                je = -1;
                stj = -1;
            }

            
            double second_color = 1 - color_3d_blending;

            for(int i = ib; i != ie; i += sti) {
                for(int j = jb; j != je; j += stj) {


                    double red = ((((int)vert[i][j][3]) >> 16) & 0xff) * color_3d_blending;
                    double green = ((((int)vert[i][j][3]) >> 8) & 0xff) * color_3d_blending;
                    double blue = (((int)vert[i][j][3]) & 0xff) * color_3d_blending;

                    if(Norm1z[i][j][0] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i + 1][j][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i + 1][j][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/
                    }

                    if(Norm1z[i][j][1] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i][j + 1][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i][j + 1][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/

                    }
                }
            }

            thread_calculated = image_size * image_size;
        }

    }

    private void drawJuliaPolar3D(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;
        double mod;
        double dx = image_size / (double)n1;

        double start;
        double center = Math.log(size);

        double f2, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / n1;

        double mulx = muly * height_ratio;

        start = center - mulx * n1 / 2.0;


        int x, y, loc, counter = 0;

        int condition = detail * detail;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            f2 = y * muly;
            sf2 = Math.sin(f2);
            cf2 = Math.cos(f2);

            r2 = Math.exp(x * mulx + start);

            vert[x][y][0] = (float)(dx * x - w2);
            vert[x][y][2] = (float)(dx * y - w2);
            temp = fractal.calculateJulia3D(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
            vert[x][y][1] = (float)(temp[0] * d3_height_scale + d3_height_offset);
            vert[x][y][3] = temp[1] == max_iterations ? fractal_color : palette_color.getPaletteColor(temp[1] + color_cycling_location);

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }


        } while(true);


        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        double ct = Math.cos(fiX), cf = Math.cos(fiY), st = Math.sin(fiX), sf = Math.sin(fiY);
        double m00 = scale * cf, m02 = scale * sf, m10 = scale * st * sf, m11 = scale * ct, m12 = -scale * st * cf;
        m20 = -ct * sf;
        m21 = st;
        m22 = ct * cf;

        for(x = FROMx; x < TOx; x++) {
            for(y = FROMy; y < TOy; y++) {
                if(x < n1 && y < n1) {
                    Norm[x][y][0][0] = vert[x][y][1] - vert[x + 1][y][1];
                    Norm[x][y][0][1] = (float)(dx);
                    Norm[x][y][0][2] = vert[x + 1][y][1] - vert[x + 1][y + 1][1];
                    mod = Math.sqrt(Norm[x][y][0][0] * Norm[x][y][0][0] + Norm[x][y][0][1] * Norm[x][y][0][1] + Norm[x][y][0][2] * Norm[x][y][0][2]) / 255.5;
                    Norm[x][y][0][0] /= mod;
                    Norm[x][y][0][1] /= mod;
                    Norm[x][y][0][2] /= mod;
                    Norm[x][y][1][0] = vert[x][y + 1][1] - vert[x + 1][y + 1][1];
                    Norm[x][y][1][1] = (float)(dx);
                    Norm[x][y][1][2] = vert[x][y][1] - vert[x][y + 1][1];
                    mod = Math.sqrt(Norm[x][y][1][0] * Norm[x][y][1][0] + Norm[x][y][1][1] * Norm[x][y][1][1] + Norm[x][y][1][2] * Norm[x][y][1][2]) / 255.5;
                    Norm[x][y][1][0] /= mod;
                    Norm[x][y][1][1] /= mod;
                    Norm[x][y][1][2] /= mod;

                    Norm1z[x][y][0] = (float)(m20 * Norm[x][y][0][0] + m21 * Norm[x][y][0][1] + m22 * Norm[x][y][0][2]);
                    Norm1z[x][y][1] = (float)(m20 * Norm[x][y][1][0] + m21 * Norm[x][y][1][1] + m22 * Norm[x][y][1][2]);
                }
                vert1[x][y][0] = (float)(m00 * vert[x][y][0] + m02 * vert[x][y][2]);
                vert1[x][y][1] = (float)(m10 * vert[x][y][0] + m11 * vert[x][y][1] + m12 * vert[x][y][2]);
            }
        }

        int sync3 = synchronization3.incrementAndGet();

        if(sync3 == ptr.getNumberOfThreads()) {

            int[] xPol = new int[3];
            int[] yPol = new int[3];

            Graphics2D g = image.createGraphics();

            int ib = 0, ie = n1, sti = 1, jb = 0, je = n1, stj = 1;

            if(m20 < 0) {
                ib = n1;
                ie = -1;
                sti = -1;
            }

            if(m22 < 0) {
                jb = n1;
                je = -1;
                stj = -1;
            }

            
            double second_color = 1 - color_3d_blending;

            for(int i = ib; i != ie; i += sti) {
                for(int j = jb; j != je; j += stj) {


                    double red = ((((int)vert[i][j][3]) >> 16) & 0xff) * color_3d_blending;
                    double green = ((((int)vert[i][j][3]) >> 8) & 0xff) * color_3d_blending;
                    double blue = (((int)vert[i][j][3]) & 0xff) * color_3d_blending;

                    if(Norm1z[i][j][0] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i + 1][j][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i + 1][j][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/
                    }

                    if(Norm1z[i][j][1] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i][j + 1][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i][j + 1][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/

                    }
                }
            }

            thread_calculated = image_size * image_size;
        }

    }

    private void drawFractalPolar3DAntialiased(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;
        double mod;
        double dx = image_size / (double)n1;

        double start;
        double center = Math.log(size);

        double f2, sf2, cf2, r2, sf3, cf3, r3;
        double muly = (2 * circle_period * Math.PI) / n1;

        double mulx = muly * height_ratio;

        start = center - mulx * n1 / 2.0;


        int x, y, loc, counter = 0;

        int condition = detail * detail;

        int red, green, blue, color;

        double height;

        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;

        double exp_x_antialiasing_size = Math.exp(x_antialiasing_size);
        double exp_inv_x_antialiasing_size = 1 / exp_x_antialiasing_size;

        double exp_x_antialiasing_size_x2 = exp_x_antialiasing_size * exp_x_antialiasing_size;
        double exp_inv_x_antialiasing_size_x2 = 1 / exp_x_antialiasing_size_x2;

        double antialiasing_x[] = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
            exp_inv_x_antialiasing_size, exp_x_antialiasing_size, 1, 1,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, 1, 1, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};


        double sin_y_antialiasing_size = Math.sin(y_antialiasing_size);
        double cos_y_antialiasing_size = Math.cos(y_antialiasing_size);

        double sin_inv_y_antialiasing_size = -sin_y_antialiasing_size;
        double cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        double sin_y_antialiasing_size_x2 = 2 * sin_y_antialiasing_size * cos_y_antialiasing_size;
        double cos_y_antialiasing_size_x2 = 2 * cos_y_antialiasing_size * cos_y_antialiasing_size - 1;

        double sin_inv_y_antialiasing_size_x2 = -sin_y_antialiasing_size_x2;
        double cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;


        double antialiasing_y_sin[] = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
            0, 0, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
            sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2,
            sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};


        double antialiasing_y_cos[] = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
            1, 1, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
            cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2,
            cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            f2 = y * muly;
            sf2 = Math.sin(f2);
            cf2 = Math.cos(f2);

            r2 = Math.exp(x * mulx + start);

            vert[x][y][0] = (float)(dx * x - w2);
            vert[x][y][2] = (float)(dx * y - w2);
            temp = fractal.calculateFractal3D(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
            height = temp[0] * d3_height_scale + d3_height_offset;
            color = temp[1] == max_iterations ? fractal_color : palette_color.getPaletteColor(temp[1] + color_cycling_location);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int k = 0; k < filters_options_vals[MainWindow.ANTIALIASING]; k++) {

                sf3 = sf2 * antialiasing_y_cos[k] + cf2 * antialiasing_y_sin[k];
                cf3 = cf2 * antialiasing_y_cos[k] - sf2 * antialiasing_y_sin[k];

                r3 = r2 * antialiasing_x[k];

                temp = fractal.calculateFractal3D(new Complex(xcenter + r3 * cf3, ycenter + r3 * sf3));
                color = temp[1] == max_iterations ? fractal_color : palette_color.getPaletteColor(temp[1] + color_cycling_location);

                height += temp[0] * d3_height_scale + d3_height_offset;
                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            vert[x][y][1] = (float)(height / temp_samples);
            vert[x][y][3] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }


        } while(true);


        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        double ct = Math.cos(fiX), cf = Math.cos(fiY), st = Math.sin(fiX), sf = Math.sin(fiY);
        double m00 = scale * cf, m02 = scale * sf, m10 = scale * st * sf, m11 = scale * ct, m12 = -scale * st * cf;
        m20 = -ct * sf;
        m21 = st;
        m22 = ct * cf;

        for(x = FROMx; x < TOx; x++) {
            for(y = FROMy; y < TOy; y++) {
                if(x < n1 && y < n1) {
                    Norm[x][y][0][0] = vert[x][y][1] - vert[x + 1][y][1];
                    Norm[x][y][0][1] = (float)(dx);
                    Norm[x][y][0][2] = vert[x + 1][y][1] - vert[x + 1][y + 1][1];
                    mod = Math.sqrt(Norm[x][y][0][0] * Norm[x][y][0][0] + Norm[x][y][0][1] * Norm[x][y][0][1] + Norm[x][y][0][2] * Norm[x][y][0][2]) / 255.5;
                    Norm[x][y][0][0] /= mod;
                    Norm[x][y][0][1] /= mod;
                    Norm[x][y][0][2] /= mod;
                    Norm[x][y][1][0] = vert[x][y + 1][1] - vert[x + 1][y + 1][1];
                    Norm[x][y][1][1] = (float)(dx);
                    Norm[x][y][1][2] = vert[x][y][1] - vert[x][y + 1][1];
                    mod = Math.sqrt(Norm[x][y][1][0] * Norm[x][y][1][0] + Norm[x][y][1][1] * Norm[x][y][1][1] + Norm[x][y][1][2] * Norm[x][y][1][2]) / 255.5;
                    Norm[x][y][1][0] /= mod;
                    Norm[x][y][1][1] /= mod;
                    Norm[x][y][1][2] /= mod;

                    Norm1z[x][y][0] = (float)(m20 * Norm[x][y][0][0] + m21 * Norm[x][y][0][1] + m22 * Norm[x][y][0][2]);
                    Norm1z[x][y][1] = (float)(m20 * Norm[x][y][1][0] + m21 * Norm[x][y][1][1] + m22 * Norm[x][y][1][2]);
                }
                vert1[x][y][0] = (float)(m00 * vert[x][y][0] + m02 * vert[x][y][2]);
                vert1[x][y][1] = (float)(m10 * vert[x][y][0] + m11 * vert[x][y][1] + m12 * vert[x][y][2]);
            }
        }

        int sync3 = synchronization3.incrementAndGet();

        if(sync3 == ptr.getNumberOfThreads()) {

            int[] xPol = new int[3];
            int[] yPol = new int[3];

            Graphics2D g = image.createGraphics();

            int ib = 0, ie = n1, sti = 1, jb = 0, je = n1, stj = 1;

            if(m20 < 0) {
                ib = n1;
                ie = -1;
                sti = -1;
            }

            if(m22 < 0) {
                jb = n1;
                je = -1;
                stj = -1;
            }

            
            double second_color = 1 - color_3d_blending;

            for(int i = ib; i != ie; i += sti) {
                for(int j = jb; j != je; j += stj) {


                    double red2 = ((((int)vert[i][j][3]) >> 16) & 0xff) * color_3d_blending;
                    double green2 = ((((int)vert[i][j][3]) >> 8) & 0xff) * color_3d_blending;
                    double blue2 = (((int)vert[i][j][3]) & 0xff) * color_3d_blending;

                    if(Norm1z[i][j][0] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i + 1][j][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i + 1][j][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/
                    }

                    if(Norm1z[i][j][1] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i][j + 1][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i][j + 1][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/

                    }
                }
            }

            thread_calculated = image_size * image_size;
        }

    }

    private void drawJuliaPolar3DAntialiased(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;
        double mod;
        double dx = image_size / (double)n1;

        double start;
        double center = Math.log(size);

        double f2, sf2, cf2, r2, sf3, cf3, r3;
        double muly = (2 * circle_period * Math.PI) / n1;

        double mulx = muly * height_ratio;

        start = center - mulx * n1 / 2.0;


        int x, y, loc, counter = 0;

        int condition = detail * detail;

        int red, green, blue, color;

        double height;

        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;

        double exp_x_antialiasing_size = Math.exp(x_antialiasing_size);
        double exp_inv_x_antialiasing_size = 1 / exp_x_antialiasing_size;

        double exp_x_antialiasing_size_x2 = exp_x_antialiasing_size * exp_x_antialiasing_size;
        double exp_inv_x_antialiasing_size_x2 = 1 / exp_x_antialiasing_size_x2;

        double antialiasing_x[] = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
            exp_inv_x_antialiasing_size, exp_x_antialiasing_size, 1, 1,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, 1, 1, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};


        double sin_y_antialiasing_size = Math.sin(y_antialiasing_size);
        double cos_y_antialiasing_size = Math.cos(y_antialiasing_size);

        double sin_inv_y_antialiasing_size = -sin_y_antialiasing_size;
        double cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        double sin_y_antialiasing_size_x2 = 2 * sin_y_antialiasing_size * cos_y_antialiasing_size;
        double cos_y_antialiasing_size_x2 = 2 * cos_y_antialiasing_size * cos_y_antialiasing_size - 1;

        double sin_inv_y_antialiasing_size_x2 = -sin_y_antialiasing_size_x2;
        double cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;


        double antialiasing_y_sin[] = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
            0, 0, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
            sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2,
            sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};


        double antialiasing_y_cos[] = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
            1, 1, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
            cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2,
            cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            f2 = y * muly;
            sf2 = Math.sin(f2);
            cf2 = Math.cos(f2);

            r2 = Math.exp(x * mulx + start);

            vert[x][y][0] = (float)(dx * x - w2);
            vert[x][y][2] = (float)(dx * y - w2);
            temp = fractal.calculateJulia3D(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
            height = temp[0] * d3_height_scale + d3_height_offset;
            color = temp[1] == max_iterations ? fractal_color : palette_color.getPaletteColor(temp[1] + color_cycling_location);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int k = 0; k < filters_options_vals[MainWindow.ANTIALIASING]; k++) {

                sf3 = sf2 * antialiasing_y_cos[k] + cf2 * antialiasing_y_sin[k];
                cf3 = cf2 * antialiasing_y_cos[k] - sf2 * antialiasing_y_sin[k];

                r3 = r2 * antialiasing_x[k];

                temp = fractal.calculateJulia3D(new Complex(xcenter + r3 * cf3, ycenter + r3 * sf3));
                color = temp[1] == max_iterations ? fractal_color : palette_color.getPaletteColor(temp[1] + color_cycling_location);

                height += temp[0] * d3_height_scale + d3_height_offset;
                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            vert[x][y][1] = (float)(height / temp_samples);
            vert[x][y][3] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }


        } while(true);


        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        double ct = Math.cos(fiX), cf = Math.cos(fiY), st = Math.sin(fiX), sf = Math.sin(fiY);
        double m00 = scale * cf, m02 = scale * sf, m10 = scale * st * sf, m11 = scale * ct, m12 = -scale * st * cf;
        m20 = -ct * sf;
        m21 = st;
        m22 = ct * cf;

        for(x = FROMx; x < TOx; x++) {
            for(y = FROMy; y < TOy; y++) {
                if(x < n1 && y < n1) {
                    Norm[x][y][0][0] = vert[x][y][1] - vert[x + 1][y][1];
                    Norm[x][y][0][1] = (float)(dx);
                    Norm[x][y][0][2] = vert[x + 1][y][1] - vert[x + 1][y + 1][1];
                    mod = Math.sqrt(Norm[x][y][0][0] * Norm[x][y][0][0] + Norm[x][y][0][1] * Norm[x][y][0][1] + Norm[x][y][0][2] * Norm[x][y][0][2]) / 255.5;
                    Norm[x][y][0][0] /= mod;
                    Norm[x][y][0][1] /= mod;
                    Norm[x][y][0][2] /= mod;
                    Norm[x][y][1][0] = vert[x][y + 1][1] - vert[x + 1][y + 1][1];
                    Norm[x][y][1][1] = (float)(dx);
                    Norm[x][y][1][2] = vert[x][y][1] - vert[x][y + 1][1];
                    mod = Math.sqrt(Norm[x][y][1][0] * Norm[x][y][1][0] + Norm[x][y][1][1] * Norm[x][y][1][1] + Norm[x][y][1][2] * Norm[x][y][1][2]) / 255.5;
                    Norm[x][y][1][0] /= mod;
                    Norm[x][y][1][1] /= mod;
                    Norm[x][y][1][2] /= mod;

                    Norm1z[x][y][0] = (float)(m20 * Norm[x][y][0][0] + m21 * Norm[x][y][0][1] + m22 * Norm[x][y][0][2]);
                    Norm1z[x][y][1] = (float)(m20 * Norm[x][y][1][0] + m21 * Norm[x][y][1][1] + m22 * Norm[x][y][1][2]);
                }
                vert1[x][y][0] = (float)(m00 * vert[x][y][0] + m02 * vert[x][y][2]);
                vert1[x][y][1] = (float)(m10 * vert[x][y][0] + m11 * vert[x][y][1] + m12 * vert[x][y][2]);
            }
        }

        int sync3 = synchronization3.incrementAndGet();

        if(sync3 == ptr.getNumberOfThreads()) {

            int[] xPol = new int[3];
            int[] yPol = new int[3];

            Graphics2D g = image.createGraphics();

            int ib = 0, ie = n1, sti = 1, jb = 0, je = n1, stj = 1;

            if(m20 < 0) {
                ib = n1;
                ie = -1;
                sti = -1;
            }

            if(m22 < 0) {
                jb = n1;
                je = -1;
                stj = -1;
            }

            
            double second_color = 1 - color_3d_blending;

            for(int i = ib; i != ie; i += sti) {
                for(int j = jb; j != je; j += stj) {


                    double red2 = ((((int)vert[i][j][3]) >> 16) & 0xff) * color_3d_blending;
                    double green2 = ((((int)vert[i][j][3]) >> 8) & 0xff) * color_3d_blending;
                    double blue2 = (((int)vert[i][j][3]) & 0xff) * color_3d_blending;

                    if(Norm1z[i][j][0] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i + 1][j][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i + 1][j][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/
                    }

                    if(Norm1z[i][j][1] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i][j + 1][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i][j + 1][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/

                    }
                }
            }

            thread_calculated = image_size * image_size;
        }

    }

    private void drawFractal3DAntialiased(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;


        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;
        double mod;
        double dx = image_size / (double)n1, dr_x = size / n1, dr_y = (size * height_ratio) / n1;


        int x, y, loc, counter = 0;

        int condition = detail * detail;

        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;

        int red, green, blue, color;

        double temp_x0, temp_y0, height;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};


        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            vert[x][y][0] = (float)(dx * x - w2);
            vert[x][y][2] = (float)(dx * y - w2);
            temp = fractal.calculateFractal3D(new Complex(temp_x0 = temp_xcenter_size + dr_x * x, temp_y0 = temp_ycenter_size + dr_y * y));
            height = temp[0] * d3_height_scale + d3_height_offset;
            color = temp[1] == max_iterations ? fractal_color : palette_color.getPaletteColor(temp[1] + color_cycling_location);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int k = 0; k < filters_options_vals[MainWindow.ANTIALIASING]; k++) {
                temp = fractal.calculateFractal3D(new Complex(temp_x0 + antialiasing_x[k], temp_y0 + antialiasing_y[k]));
                color = temp[1] == max_iterations ? fractal_color : palette_color.getPaletteColor(temp[1] + color_cycling_location);

                height += temp[0] * d3_height_scale + d3_height_offset;;
                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            vert[x][y][1] = (float)(height / temp_samples);
            vert[x][y][3] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }


        } while(true);


        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        double ct = Math.cos(fiX), cf = Math.cos(fiY), st = Math.sin(fiX), sf = Math.sin(fiY);
        double m00 = scale * cf, m02 = scale * sf, m10 = scale * st * sf, m11 = scale * ct, m12 = -scale * st * cf;
        m20 = -ct * sf;
        m21 = st;
        m22 = ct * cf;

        for(x = FROMx; x < TOx; x++) {
            for(y = FROMy; y < TOy; y++) {
                if(x < n1 && y < n1) {
                    Norm[x][y][0][0] = vert[x][y][1] - vert[x + 1][y][1];
                    Norm[x][y][0][1] = (float)dx;
                    Norm[x][y][0][2] = vert[x + 1][y][1] - vert[x + 1][y + 1][1];
                    mod = Math.sqrt(Norm[x][y][0][0] * Norm[x][y][0][0] + Norm[x][y][0][1] * Norm[x][y][0][1] + Norm[x][y][0][2] * Norm[x][y][0][2]) / 255.5;
                    Norm[x][y][0][0] /= mod;
                    Norm[x][y][0][1] /= mod;
                    Norm[x][y][0][2] /= mod;
                    Norm[x][y][1][0] = vert[x][y + 1][1] - vert[x + 1][y + 1][1];
                    Norm[x][y][1][1] = (float)dx;
                    Norm[x][y][1][2] = vert[x][y][1] - vert[x][y + 1][1];
                    mod = Math.sqrt(Norm[x][y][1][0] * Norm[x][y][1][0] + Norm[x][y][1][1] * Norm[x][y][1][1] + Norm[x][y][1][2] * Norm[x][y][1][2]) / 255.5;
                    Norm[x][y][1][0] /= mod;
                    Norm[x][y][1][1] /= mod;
                    Norm[x][y][1][2] /= mod;

                    Norm1z[x][y][0] = (float)(m20 * Norm[x][y][0][0] + m21 * Norm[x][y][0][1] + m22 * Norm[x][y][0][2]);
                    Norm1z[x][y][1] = (float)(m20 * Norm[x][y][1][0] + m21 * Norm[x][y][1][1] + m22 * Norm[x][y][1][2]);
                }
                vert1[x][y][0] = (float)(m00 * vert[x][y][0] + m02 * vert[x][y][2]);
                vert1[x][y][1] = (float)(m10 * vert[x][y][0] + m11 * vert[x][y][1] + m12 * vert[x][y][2]);
            }
        }


        int sync3 = synchronization3.incrementAndGet();

        if(sync3 == ptr.getNumberOfThreads()) {

            int[] xPol = new int[3];
            int[] yPol = new int[3];

            Graphics2D g = image.createGraphics();

            int ib = 0, ie = n1, sti = 1, jb = 0, je = n1, stj = 1;

            if(m20 < 0) {
                ib = n1;
                ie = -1;
                sti = -1;
            }

            if(m22 < 0) {
                jb = n1;
                je = -1;
                stj = -1;
            }

            
            double second_color = 1 - color_3d_blending;

            for(int i = ib; i != ie; i += sti) {
                for(int j = jb; j != je; j += stj) {


                    double red2 = ((((int)vert[i][j][3]) >> 16) & 0xff) * color_3d_blending;
                    double green2 = ((((int)vert[i][j][3]) >> 8) & 0xff) * color_3d_blending;
                    double blue2 = (((int)vert[i][j][3]) & 0xff) * color_3d_blending;

                    if(Norm1z[i][j][0] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i + 1][j][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i + 1][j][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/

                    }

                    if(Norm1z[i][j][1] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i][j + 1][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i][j + 1][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/

                    }
                }
            }

            thread_calculated = image_size * image_size;
        }

    }

    private void drawFractalAntialiasedBruteForce(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;

        int pixel_percent = image_size * image_size / 100;

        //better Brute force with antialiasing
        int x, y, loc, counter = 0;
        int color;

        double temp_result;
        double temp_x0, temp_y0;

        int red, green, blue;

        int condition = image_size * image_size;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
            temp_y0 = temp_ycenter_size + temp_size_image_size_y * y;

            temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
            color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {
                temp_result = fractal.calculateFractal(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;
    }

    private void drawFractalAntialiasedBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;

        int pixel_percent = image_size * image_size / 100;

        int color;

        double temp_result;

        int red, green, blue;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        double nextX, nextY, start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};


        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;



        for(y = FROMy; y < TOy; y++) {
            temp_y0 = temp_ycenter_size + y * temp_size_image_size_y;
            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {
                    temp_x0 = temp_xcenter_size + x * temp_size_image_size_x;
                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations[pix] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                    color = start_val == max_iterations ? fractal_color : palette_color.getPaletteColor(start_val + color_cycling_location);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {
                        temp_result = fractal.calculateFractal(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                        color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    startColor = rgbs[pix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                    drawing_done++;
                    thread_calculated++;
                    /*ptr.getMainPanel().repaint();
                    try {
                    Thread.sleep(1); //demo
                    }
                    catch (InterruptedException ex) {}*/

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                        curPix = startPix = startPix - image_size;
                        iy--;
                    }

                    temp_ix = ix;
                    temp_iy = iy;

                    do {                                            // tracing cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];

                            nextX = temp_xcenter_size + next_ix * temp_size_image_size_x;
                            nextY = temp_ycenter_size + next_iy * temp_size_image_size_y;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                temp_result = image_iterations[nextPix] = fractal.calculateFractal(new Complex(nextX, nextY));
                                color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {
                                    temp_result = fractal.calculateFractal(new Complex(nextX + antialiasing_x[i], nextY + antialiasing_y[i]));
                                    color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                nextColor = rgbs[nextPix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                                drawing_done++;
                                thread_calculated++;
                                /*ptr.getMainPanel().repaint();
                                try {
                                Thread.sleep(1); //demo
                                }
                                catch (InterruptedException ex) {}*/
                            }


                            if(nextColor == startColor) {
                                curDir = dir;
                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);


                    curDir = dirRight;


                    do {                                                 // 2nd cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];


                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if(rgbs[nextPix] == startColor) {           // flooding
                                curDir = dir;
                                if(dir == dirUP) {
                                    floodPix = curPix;
                                    flood_ix = temp_ix;

                                    while(true) {
                                        flood_ix++;

                                        if(flood_ix >= TOx) {
                                            break;
                                        }

                                        floodPix++;

                                        if((floodColor = rgbs[floodPix]) == culcColor) {
                                            drawing_done++;
                                            rgbs[floodPix] = startColor;
                                            image_iterations[floodPix] = start_val;
                                            /*ptr.getMainPanel().repaint();
                                            try {
                                            Thread.sleep(1); //demo
                                            }
                                            catch (InterruptedException ex) {}*/
                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }

                                    if(drawing_done / pixel_percent >= 1) {
                                        update(drawing_done);
                                        drawing_done = 0;
                                    }
                                }

                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);

                }
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }
        }

    }

    private void drawFractalAntialiased(int image_size) {

        //ptr.setWholeImageDone(true); // demo

        if(!boundary_tracing) {
            drawFractalAntialiasedBruteForce(image_size);
        }
        else {
            drawFractalAntialiasedBoundaryTracing(image_size);
        }

    }

    private void drawJuliaBruteForce(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;

        int pixel_percent = image_size * image_size / 100;

        double temp_result;

        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size + temp_size_image_size_y * y));
            rgbs[loc] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;
    }

    private void drawJuliaBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;

        int pixel_percent = image_size * image_size / 100;

        double temp_result;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};

        double nextX, nextY, start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};


        for(y = FROMy; y < TOy; y++) {
            temp_y0 = temp_ycenter_size + y * temp_size_image_size_y;
            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {
                    temp_x0 = temp_xcenter_size + x * temp_size_image_size_x;

                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                    startColor = rgbs[pix] = start_val == max_iterations ? fractal_color : palette_color.getPaletteColor(start_val + color_cycling_location);
                    drawing_done++;
                    thread_calculated++;

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                        curPix = startPix = startPix - image_size;
                        iy--;
                    }

                    temp_ix = ix;
                    temp_iy = iy;

                    do {                                            // tracing cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];

                            nextX = temp_xcenter_size + next_ix * temp_size_image_size_x;
                            nextY = temp_ycenter_size + next_iy * temp_size_image_size_y;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                temp_result = image_iterations[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                nextColor = rgbs[nextPix] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
                                drawing_done++;
                                thread_calculated++;
                            }


                            if(nextColor == startColor) {
                                curDir = dir;
                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);


                    curDir = dirRight;


                    do {                                                 // 2nd cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if(rgbs[nextPix] == startColor) {           // flooding  
                                curDir = dir;
                                if(dir == dirUP) {
                                    floodPix = curPix;
                                    flood_ix = temp_ix;

                                    while(true) {
                                        flood_ix++;

                                        if(flood_ix >= TOx) {
                                            break;
                                        }

                                        floodPix++;

                                        if((floodColor = rgbs[floodPix]) == culcColor) {
                                            drawing_done++;
                                            rgbs[floodPix] = startColor;
                                            image_iterations[floodPix] = start_val;
                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }

                                    if(drawing_done / pixel_percent >= 1) {
                                        update(drawing_done);
                                        drawing_done = 0;
                                    }
                                }

                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);

                }
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }
        }

    }

    private void drawJulia(int image_size) {

        if(!boundary_tracing) {
            drawJuliaBruteForce(image_size);
        }
        else {
            drawJuliaBoundaryTracing(image_size);
        }

    }

    private void drawJulia3D(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;


        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;
        double mod;
        double dx = image_size / (double)n1, dr_x = size / n1, dr_y = (size * height_ratio) / n1;


        int x, y, loc, counter = 0;

        int condition = detail * detail;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            vert[x][y][0] = (float)(dx * x - w2);
            vert[x][y][2] = (float)(dx * y - w2);
            temp = fractal.calculateJulia3D(new Complex(temp_xcenter_size + dr_x * x, temp_ycenter_size + dr_y * y));
            vert[x][y][1] = (float)(temp[0] * d3_height_scale + d3_height_offset);
            vert[x][y][3] = temp[1] == max_iterations ? fractal_color : palette_color.getPaletteColor(temp[1] + color_cycling_location);

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }


        } while(true);


        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        double ct = Math.cos(fiX), cf = Math.cos(fiY), st = Math.sin(fiX), sf = Math.sin(fiY);
        double m00 = scale * cf, m02 = scale * sf, m10 = scale * st * sf, m11 = scale * ct, m12 = -scale * st * cf;
        m20 = -ct * sf;
        m21 = st;
        m22 = ct * cf;

        for(x = FROMx; x < TOx; x++) {
            for(y = FROMy; y < TOy; y++) {
                if(x < n1 && y < n1) {
                    Norm[x][y][0][0] = vert[x][y][1] - vert[x + 1][y][1];
                    Norm[x][y][0][1] = (float)dx;
                    Norm[x][y][0][2] = vert[x + 1][y][1] - vert[x + 1][y + 1][1];
                    mod = Math.sqrt(Norm[x][y][0][0] * Norm[x][y][0][0] + Norm[x][y][0][1] * Norm[x][y][0][1] + Norm[x][y][0][2] * Norm[x][y][0][2]) / 255.5;
                    Norm[x][y][0][0] /= mod;
                    Norm[x][y][0][1] /= mod;
                    Norm[x][y][0][2] /= mod;
                    Norm[x][y][1][0] = vert[x][y + 1][1] - vert[x + 1][y + 1][1];
                    Norm[x][y][1][1] = (float)dx;
                    Norm[x][y][1][2] = vert[x][y][1] - vert[x][y + 1][1];
                    mod = Math.sqrt(Norm[x][y][1][0] * Norm[x][y][1][0] + Norm[x][y][1][1] * Norm[x][y][1][1] + Norm[x][y][1][2] * Norm[x][y][1][2]) / 255.5;
                    Norm[x][y][1][0] /= mod;
                    Norm[x][y][1][1] /= mod;
                    Norm[x][y][1][2] /= mod;

                    Norm1z[x][y][0] = (float)(m20 * Norm[x][y][0][0] + m21 * Norm[x][y][0][1] + m22 * Norm[x][y][0][2]);
                    Norm1z[x][y][1] = (float)(m20 * Norm[x][y][1][0] + m21 * Norm[x][y][1][1] + m22 * Norm[x][y][1][2]);
                }
                vert1[x][y][0] = (float)(m00 * vert[x][y][0] + m02 * vert[x][y][2]);
                vert1[x][y][1] = (float)(m10 * vert[x][y][0] + m11 * vert[x][y][1] + m12 * vert[x][y][2]);
            }
        }

        int sync3 = synchronization3.incrementAndGet();

        if(sync3 == ptr.getNumberOfThreads()) {

            int[] xPol = new int[3];
            int[] yPol = new int[3];

            Graphics2D g = image.createGraphics();

            int ib = 0, ie = n1, sti = 1, jb = 0, je = n1, stj = 1;

            if(m20 < 0) {
                ib = n1;
                ie = -1;
                sti = -1;
            }

            if(m22 < 0) {
                jb = n1;
                je = -1;
                stj = -1;
            }

            
            double second_color = 1 - color_3d_blending;

            for(int i = ib; i != ie; i += sti) {
                for(int j = jb; j != je; j += stj) {


                    double red = ((((int)vert[i][j][3]) >> 16) & 0xff) * color_3d_blending;
                    double green = ((((int)vert[i][j][3]) >> 8) & 0xff) * color_3d_blending;
                    double blue = (((int)vert[i][j][3]) & 0xff) * color_3d_blending;

                    if(Norm1z[i][j][0] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i + 1][j][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i + 1][j][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/

                    }

                    if(Norm1z[i][j][1] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i][j + 1][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i][j + 1][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/

                    }
                }
            }

            thread_calculated = image_size * image_size;
        }

    }

    private void drawJulia3DAntialiased(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;


        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;
        double mod;
        double dx = image_size / (double)n1, dr_x = size / n1, dr_y = (size * height_ratio) / n1;


        int x, y, loc, counter = 0;

        int condition = detail * detail;

        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;

        int red, green, blue, color;

        double temp_x0, temp_y0, height;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            vert[x][y][0] = (float)(dx * x - w2);
            vert[x][y][2] = (float)(dx * y - w2);
            temp = fractal.calculateJulia3D(new Complex(temp_x0 = temp_xcenter_size + dr_x * x, temp_y0 = temp_ycenter_size + dr_y * y));
            height = temp[0] * d3_height_scale + d3_height_offset;
            color = temp[1] == max_iterations ? fractal_color : palette_color.getPaletteColor(temp[1] + color_cycling_location);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int k = 0; k < filters_options_vals[MainWindow.ANTIALIASING]; k++) {
                temp = fractal.calculateJulia3D(new Complex(temp_x0 + antialiasing_x[k], temp_y0 + antialiasing_y[k]));
                color = temp[1] == max_iterations ? fractal_color : palette_color.getPaletteColor(temp[1] + color_cycling_location);

                height += temp[0] * d3_height_scale + d3_height_offset;
                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            vert[x][y][1] = (float)(height / temp_samples);
            vert[x][y][3] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }


        } while(true);


        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        double ct = Math.cos(fiX), cf = Math.cos(fiY), st = Math.sin(fiX), sf = Math.sin(fiY);
        double m00 = scale * cf, m02 = scale * sf, m10 = scale * st * sf, m11 = scale * ct, m12 = -scale * st * cf;
        m20 = -ct * sf;
        m21 = st;
        m22 = ct * cf;

        for(x = FROMx; x < TOx; x++) {
            for(y = FROMy; y < TOy; y++) {
                if(x < n1 && y < n1) {
                    Norm[x][y][0][0] = vert[x][y][1] - vert[x + 1][y][1];
                    Norm[x][y][0][1] = (float)dx;
                    Norm[x][y][0][2] = vert[x + 1][y][1] - vert[x + 1][y + 1][1];
                    mod = Math.sqrt(Norm[x][y][0][0] * Norm[x][y][0][0] + Norm[x][y][0][1] * Norm[x][y][0][1] + Norm[x][y][0][2] * Norm[x][y][0][2]) / 255.5;
                    Norm[x][y][0][0] /= mod;
                    Norm[x][y][0][1] /= mod;
                    Norm[x][y][0][2] /= mod;
                    Norm[x][y][1][0] = vert[x][y + 1][1] - vert[x + 1][y + 1][1];
                    Norm[x][y][1][1] = (float)dx;
                    Norm[x][y][1][2] = vert[x][y][1] - vert[x][y + 1][1];
                    mod = Math.sqrt(Norm[x][y][1][0] * Norm[x][y][1][0] + Norm[x][y][1][1] * Norm[x][y][1][1] + Norm[x][y][1][2] * Norm[x][y][1][2]) / 255.5;
                    Norm[x][y][1][0] /= mod;
                    Norm[x][y][1][1] /= mod;
                    Norm[x][y][1][2] /= mod;

                    Norm1z[x][y][0] = (float)(m20 * Norm[x][y][0][0] + m21 * Norm[x][y][0][1] + m22 * Norm[x][y][0][2]);
                    Norm1z[x][y][1] = (float)(m20 * Norm[x][y][1][0] + m21 * Norm[x][y][1][1] + m22 * Norm[x][y][1][2]);
                }
                vert1[x][y][0] = (float)(m00 * vert[x][y][0] + m02 * vert[x][y][2]);
                vert1[x][y][1] = (float)(m10 * vert[x][y][0] + m11 * vert[x][y][1] + m12 * vert[x][y][2]);
            }
        }

        int sync3 = synchronization3.incrementAndGet();

        if(sync3 == ptr.getNumberOfThreads()) {

            int[] xPol = new int[3];
            int[] yPol = new int[3];

            Graphics2D g = image.createGraphics();

            int ib = 0, ie = n1, sti = 1, jb = 0, je = n1, stj = 1;

            if(m20 < 0) {
                ib = n1;
                ie = -1;
                sti = -1;
            }

            if(m22 < 0) {
                jb = n1;
                je = -1;
                stj = -1;
            }

            
            double second_color = 1 - color_3d_blending;

            for(int i = ib; i != ie; i += sti) {
                for(int j = jb; j != je; j += stj) {


                    double red2 = ((((int)vert[i][j][3]) >> 16) & 0xff) * color_3d_blending;
                    double green2 = ((((int)vert[i][j][3]) >> 8) & 0xff) * color_3d_blending;
                    double blue2 = (((int)vert[i][j][3]) & 0xff) * color_3d_blending;

                    if(Norm1z[i][j][0] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i + 1][j][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i + 1][j][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/

                    }

                    if(Norm1z[i][j][1] > 0) {
                        xPol[0] = w2 + (int)vert1[i][j][0];
                        xPol[1] = w2 + (int)vert1[i][j + 1][0];
                        xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                        yPol[0] = w2 - (int)vert1[i][j][1];
                        yPol[1] = w2 - (int)vert1[i][j + 1][1];
                        yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                        //if(d3_draw_method == 0) {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                        /*}
                        else if(d3_draw_method == 1) {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.drawPolygon(xPol, yPol, 3);
                        }
                        else {
                            g.setColor(new Color((int)(red2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green2 + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue2 + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));
                            g.fillPolygon(xPol, yPol, 3);
                            g.setColor(Color.WHITE);
                            g.drawPolygon(xPol, yPol, 3);
                        }*/

                    }
                }
            }

            thread_calculated = image_size * image_size;
        }

    }

    private void drawJuliaAntialiasedBruteForce(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;

        int pixel_percent = image_size * image_size / 100;

        int x, y, loc, counter = 0;
        int color;

        double temp_result;
        double temp_x0, temp_y0;

        int red, green, blue;

        int condition = image_size * image_size;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
            temp_y0 = temp_ycenter_size + temp_size_image_size_y * y;

            temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
            color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {
                temp_result = fractal.calculateJulia(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;
    }

    private void drawJuliaAntialiasedBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;

        int pixel_percent = image_size * image_size / 100;

        int color;

        double temp_result;

        int red, green, blue;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        double nextX, nextY, start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;


        for(y = FROMy; y < TOy; y++) {
            temp_y0 = temp_ycenter_size + y * temp_size_image_size_y;
            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {
                    temp_x0 = temp_xcenter_size + x * temp_size_image_size_x;
                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                    color = start_val == max_iterations ? fractal_color : palette_color.getPaletteColor(start_val + color_cycling_location);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {
                        temp_result = fractal.calculateJulia(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                        color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    startColor = rgbs[pix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                    drawing_done++;
                    thread_calculated++;


                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                        curPix = startPix = startPix - image_size;
                        iy--;
                    }

                    temp_ix = ix;
                    temp_iy = iy;

                    do {                                            // tracing cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];

                            nextX = temp_xcenter_size + next_ix * temp_size_image_size_x;
                            nextY = temp_ycenter_size + next_iy * temp_size_image_size_y;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                temp_result = image_iterations[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {
                                    temp_result = fractal.calculateJulia(new Complex(nextX + antialiasing_x[i], nextY + antialiasing_y[i]));
                                    color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                nextColor = rgbs[nextPix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                                drawing_done++;
                                thread_calculated++;
                            }


                            if(nextColor == startColor) {
                                curDir = dir;
                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);


                    curDir = dirRight;


                    do {                                                 // 2nd cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];


                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if(rgbs[nextPix] == startColor) {           // flooding
                                curDir = dir;
                                if(dir == dirUP) {
                                    floodPix = curPix;
                                    flood_ix = temp_ix;

                                    while(true) {
                                        flood_ix++;

                                        if(flood_ix >= TOx) {
                                            break;
                                        }

                                        floodPix++;

                                        if((floodColor = rgbs[floodPix]) == culcColor) {
                                            drawing_done++;
                                            rgbs[floodPix] = startColor;
                                            image_iterations[floodPix] = start_val;
                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }

                                    if(drawing_done / pixel_percent >= 1) {
                                        update(drawing_done);
                                        drawing_done = 0;
                                    }
                                }

                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);

                }
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }
        }

    }

    private void drawJuliaAntialiased(int image_size) {

        if(!boundary_tracing) {
            drawJuliaAntialiasedBruteForce(image_size);
        }
        else {
            drawJuliaAntialiasedBoundaryTracing(image_size);
        }

    }

    private void fastJuliaDraw() {

        int image_size = image.getHeight();

        if(bump_map || fake_de) {
            if(fast_julia_filters && filters[MainWindow.ANTIALIASING]) {
                drawFastJuliaAntialiasedPostProcess(image_size);
            }
            else {
                drawFastJuliaPostProcess(image_size);
            }
        }
        else {
            if(fast_julia_filters && filters[MainWindow.ANTIALIASING]) {
                drawFastJuliaAntialiased(image_size);
            }
            else {
                drawFastJulia(image_size);
            }
        }

        int done = synchronization.incrementAndGet();


        if(done == ptr.getNumberOfThreads()) {

            if(fast_julia_filters) {
                applyFilters();
            }


            Graphics2D graphics = image.createGraphics();
            graphics.setColor(Color.BLACK);
            graphics.drawRect(0, 0, image_size - 1, image_size - 1);
            ptr.getMainPanel().getGraphics().drawImage(image, ptr.getScrollPane().getHorizontalScrollBar().getValue(), ptr.getScrollPane().getVerticalScrollBar().getValue(), null);

        }

    }

    private void fastJuliaDrawPolar() {

        int image_size = image.getHeight();

        if(bump_map || fake_de) {
            if(fast_julia_filters && filters[MainWindow.ANTIALIASING]) {
                drawFastJuliaPolarAntialiasedPostProcess(image_size);
            }
            else {
                drawFastJuliaPolarPostProcess(image_size);
            }
        }
        else {
            if(fast_julia_filters && filters[MainWindow.ANTIALIASING]) {
                drawFastJuliaPolarAntialiased(image_size);
            }
            else {
                drawFastJuliaPolar(image_size);
            }
        }

        int done = synchronization.incrementAndGet();


        if(done == ptr.getNumberOfThreads()) {

            if(fast_julia_filters) {
                applyFilters();
            }


            Graphics2D graphics = image.createGraphics();
            graphics.setColor(Color.BLACK);
            graphics.drawRect(0, 0, image_size - 1, image_size - 1);
            ptr.getMainPanel().getGraphics().drawImage(image, ptr.getScrollPane().getHorizontalScrollBar().getValue(), ptr.getScrollPane().getVerticalScrollBar().getValue(), null);

        }

    }

    private void drawFastJuliaBruteForce(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;

        double temp_result;

        int x, y, loc;

        int condition = image_size * image_size;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            temp_result = image_iterations_fast_julia[loc] = fractal.calculateJulia(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size + temp_size_image_size_y * y));
            rgbs[loc] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

        } while(true);

    }
  
    private int pseudoDistanceEstimation(double[] image_iterations, int color, int i, int j, int image_size) {
        int i0 = i;
        int j0 = j;
        int ix = i + 1;
        int jx = j;
        int sx = 1;
        int iy = i;
        int jy = j + 1;
        int sy = 1;

        if(ix == image_size) {
            ix -= 2;
            sx = -1;
        }
        if(jy == image_size) {
            jy -= 2;
            sx = -1;
        }
        double n0 = image_iterations[i0 * image_size + j0];
        double coef;

        double zz = 1 / fake_de_factor;
        double zz2 = zz * zz;

        if(n0 == max_iterations && color == fractal_color) {
            coef = 1.0;
        }
        else if(n0 == max_iterations && color != fractal_color) {
            coef = 0.15;
        }
        else {
            double nx = image_iterations[ix * image_size + jx];
            double ny = image_iterations[iy * image_size + jy];
            
            double zx = sx * (nx - n0);
            double zy = sy * (ny - n0);
            double z = Math.sqrt(zx * zx + zy * zy + zz2);
            coef = zz / z;
        }

        int r = color & 0xFF0000;
        int g = color & 0x00FF00;
        int b = color & 0x0000FF;

        int fc_red = fractal_color & 0xFF0000;
        int fc_green = fractal_color & 0x00FF00;
        int fc_blue = fractal_color & 0x0000FF;

        double inv_coef = 1 - coef;

        final int ret = 0xff000000 | (int)(r * coef + fc_red * inv_coef + 0.5) & 0xFF0000 | (int)(g * coef + fc_green * inv_coef + 0.5) & 0x00FF00 | (int)(b * coef + fc_blue * inv_coef + 0.5);

        return ret;

    }

    private void applyPostProcessing(int image_size) {

        double gradCorr, sizeCorr = 0, lightAngleRadians, lightx = 0, lighty = 0;

        if(bump_map) {
            gradCorr = Math.pow(2, (bumpMappingStrength - DEFAULT_BUMP_MAPPING_STRENGTH) * 0.05);
            sizeCorr = image_size / Math.pow(2, (MAX_BUMP_MAPPING_DEPTH - bumpMappingDepth) * 0.16);
            lightAngleRadians = Math.toRadians(lightDirectionDegrees);
            lightx = Math.cos(lightAngleRadians) * gradCorr;
            lighty = Math.sin(lightAngleRadians) * gradCorr;
        }


        double gradx, grady, dotp, gradAbs, cosAngle, smoothGrad;
        int modified;

        for(int y = FROMy; y < TOy; y++) {
            for(int x = FROMx; x < TOx; x++) {
                int index = y * image_size + x;

                modified = rgbs[index];

                if(bump_map) {
                    gradx = getGradientX(image_iterations, index, image_size);
                    grady = getGradientY(image_iterations, index, image_size);

                    dotp = gradx * lightx + grady * lighty;
                    if(dotp != 0.0) {

                        gradAbs = Math.sqrt(gradx * gradx + grady * grady);
                        cosAngle = dotp / gradAbs;
                        smoothGrad = -2.3562 / (gradAbs * sizeCorr + 1.5) + 1.57;
                        //final double smoothGrad = Math.atan(gradAbs * sizeCorr);
                        modified = changeBrightnessOfColor(modified, cosAngle * smoothGrad);
                    }
                }

                if(fake_de) {
                    modified = pseudoDistanceEstimation(image_iterations, modified, y, x, image_size);
                }

                rgbs[index] = modified;
            }
        }
    }

    private void applyPostProcessingFastJulia(int image_size) {

        double gradCorr, sizeCorr = 0, lightAngleRadians, lightx = 0, lighty = 0;

        if(bump_map) {
            gradCorr = Math.pow(2, (bumpMappingStrength - DEFAULT_BUMP_MAPPING_STRENGTH) * 0.05);
            sizeCorr = image_size / Math.pow(2, (MAX_BUMP_MAPPING_DEPTH - bumpMappingDepth) * 0.16);
            lightAngleRadians = Math.toRadians(lightDirectionDegrees);
            lightx = Math.cos(lightAngleRadians) * gradCorr;
            lighty = Math.sin(lightAngleRadians) * gradCorr;
        }


        double gradx, grady, dotp, gradAbs, cosAngle, smoothGrad;
        int modified;

        for(int y = FROMy; y < TOy; y++) {
            for(int x = FROMx; x < TOx; x++) {
                int index = y * image_size + x;

                modified = rgbs[index];

                if(bump_map) {
                    gradx = getGradientX(image_iterations_fast_julia, index, image_size);
                    grady = getGradientY(image_iterations_fast_julia, index, image_size);

                    dotp = gradx * lightx + grady * lighty;
                    if(dotp != 0.0) {

                        gradAbs = Math.sqrt(gradx * gradx + grady * grady);
                        cosAngle = dotp / gradAbs;
                        smoothGrad = -2.3562 / (gradAbs * sizeCorr + 1.5) + 1.57;
                        //final double smoothGrad = Math.atan(gradAbs * sizeCorr);
                        modified = changeBrightnessOfColor(modified, cosAngle * smoothGrad);
                    }
                }

                if(fake_de) {
                    modified = pseudoDistanceEstimation(image_iterations_fast_julia, modified, y, x, image_size);
                }

                rgbs[index] = modified;
            }
        }
    }

    private void drawFastJuliaPolarBoundaryTracingPostProcess(int image_size) {

        drawFastJuliaPolarBoundaryTracing(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessingFastJulia(image_size);
    }

    private void drawFastJuliaPolarBruteForcePostProcess(int image_size) {

        drawFastJuliaPolarBruteForce(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessingFastJulia(image_size);

    }

    private void drawFastJuliaBoundaryTracingPostProcess(int image_size) {

        drawFastJuliaBoundaryTracing(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessingFastJulia(image_size);
    }

    private void drawFastJuliaBruteForcePostProcess(int image_size) {

        drawFastJuliaBruteForce(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessingFastJulia(image_size);

    }

    private void drawFastJuliaPostProcess(int image_size) {

        if(!boundary_tracing) {
            drawFastJuliaBruteForcePostProcess(image_size);
        }
        else {
            drawFastJuliaBoundaryTracingPostProcess(image_size);
        }

    }

    private void drawFastJuliaPolarPostProcess(int image_size) {

        if(!boundary_tracing) {
            drawFastJuliaPolarBruteForcePostProcess(image_size);
        }
        else {
            drawFastJuliaPolarBoundaryTracingPostProcess(image_size);
        }

    }

    private void drawFastJuliaAntialiasedPostProcess(int image_size) {

        if(!boundary_tracing) {
            drawFastJuliaAntialiasedBruteForcePostProcess(image_size);
        }
        else {
            drawFastJuliaAntialiasedBoundaryTracingPostProcess(image_size);
        }
    }

    private void drawFastJuliaPolarAntialiasedPostProcess(int image_size) {

        if(!boundary_tracing) {
            drawFastJuliaPolarAntialiasedBruteForcePostProcess(image_size);
        }
        else {
            drawFastJuliaPolarAntialiasedBoundaryTracingPostProcess(image_size);
        }
    }

    private void drawFastJuliaBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;

        double temp_result;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        double nextX, nextY;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        double start_val;


        for(y = FROMy; y < TOy; y++) {
            temp_y0 = temp_ycenter_size + y * temp_size_image_size_y;
            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {
                    temp_x0 = temp_xcenter_size + x * temp_size_image_size_x;
                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations_fast_julia[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                    startColor = rgbs[pix] = start_val == max_iterations ? fractal_color : palette_color.getPaletteColor(start_val + color_cycling_location);

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                        curPix = startPix = startPix - image_size;
                        iy--;
                    }

                    temp_ix = ix;
                    temp_iy = iy;

                    do {                                            // tracing cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];

                            nextX = temp_xcenter_size + next_ix * temp_size_image_size_x;
                            nextY = temp_ycenter_size + next_iy * temp_size_image_size_y;


                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                temp_result = image_iterations_fast_julia[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                nextColor = rgbs[nextPix] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
                            }


                            if(nextColor == startColor) {
                                curDir = dir;
                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);


                    curDir = dirRight;


                    do {                                                 // 2nd cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];


                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if(rgbs[nextPix] == startColor) {           // flooding
                                curDir = dir;
                                if(dir == dirUP) {
                                    floodPix = curPix;
                                    flood_ix = temp_ix;

                                    while(true) {
                                        flood_ix++;

                                        if(flood_ix >= TOx) {
                                            break;
                                        }

                                        floodPix++;

                                        if((floodColor = rgbs[floodPix]) == culcColor) {
                                            rgbs[floodPix] = startColor;
                                            image_iterations_fast_julia[floodPix] = start_val;
                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }

                                }

                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);

                }
            }

        }
    }

    private void drawFastJulia(int image_size) {

        if(!boundary_tracing) {
            drawFastJuliaBruteForce(image_size);
        }
        else {
            drawFastJuliaBoundaryTracing(image_size);
        }

    }

    private void drawFastJuliaPolar(int image_size) {

        if(!boundary_tracing) {
            drawFastJuliaPolarBruteForce(image_size);
        }
        else {
            drawFastJuliaPolarBoundaryTracing(image_size);
        }

    }

    private void drawFastJuliaPolarBruteForce(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        double temp_result;

        int x, y, loc;

        int condition = image_size * image_size;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            temp_result = image_iterations_fast_julia[loc] = fractal.calculateJulia(new Complex(xcenter + r * cf, ycenter + r * sf));
            rgbs[loc] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

        } while(true);

    }

    private void drawFastJuliaPolarBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, r2, f2, sf2, cf2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        double temp_result;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        double nextX, nextY;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        double start_val;


        for(y = FROMy; y < TOy; y++) {

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {
                    r = Math.exp(x * mulx + start);

                    temp_x0 = xcenter + r * cf;
                    temp_y0 = ycenter + r * sf;

                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations_fast_julia[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                    startColor = rgbs[pix] = start_val == max_iterations ? fractal_color : palette_color.getPaletteColor(start_val + color_cycling_location);

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                        curPix = startPix = startPix - image_size;
                        iy--;
                    }

                    temp_ix = ix;
                    temp_iy = iy;

                    do {                                            // tracing cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];

                            f2 = next_iy * muly;
                            sf2 = Math.sin(f2);
                            cf2 = Math.cos(f2);

                            r2 = Math.exp(next_ix * mulx + start);

                            nextX = xcenter + r2 * cf2;
                            nextY = ycenter + r2 * sf2;


                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                temp_result = image_iterations_fast_julia[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                nextColor = rgbs[nextPix] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
                            }


                            if(nextColor == startColor) {
                                curDir = dir;
                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);


                    curDir = dirRight;


                    do {                                                 // 2nd cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];


                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if(rgbs[nextPix] == startColor) {           // flooding
                                curDir = dir;
                                if(dir == dirUP) {
                                    floodPix = curPix;
                                    flood_ix = temp_ix;

                                    while(true) {
                                        flood_ix++;

                                        if(flood_ix >= TOx) {
                                            break;
                                        }

                                        floodPix++;

                                        if((floodColor = rgbs[floodPix]) == culcColor) {
                                            rgbs[floodPix] = startColor;
                                            image_iterations_fast_julia[floodPix] = start_val;
                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }

                                }

                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);

                }
            }

        }
    }

    private void drawFastJuliaAntialiasedBruteForce(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;

        int x, y, loc;
        int color;

        double temp_result;
        double temp_x0, temp_y0;

        int red, green, blue;

        int condition = image_size * image_size;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
            temp_y0 = temp_ycenter_size + temp_size_image_size_y * y;

            temp_result = image_iterations_fast_julia[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
            color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {
                temp_result = fractal.calculateJulia(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

        } while(true);
    }

    private void drawFastJuliaPolarAntialiasedBruteForcePostProcess(int image_size) {

        drawFastJuliaPolarAntialiasedBruteForce(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessingFastJulia(image_size);

    }

    private void drawFastJuliaPolarAntialiasedBoundaryTracingPostProcess(int image_size) {

        drawFastJuliaPolarAntialiasedBoundaryTracing(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessingFastJulia(image_size);
    }

    private void drawFastJuliaAntialiasedBruteForcePostProcess(int image_size) {

        drawFastJuliaAntialiasedBruteForce(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessingFastJulia(image_size);

    }

    private void drawFastJuliaAntialiasedBoundaryTracingPostProcess(int image_size) {

        drawFastJuliaAntialiasedBoundaryTracing(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getNumberOfThreads()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessingFastJulia(image_size);
    }

    private void drawFastJuliaAntialiasedBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;

        int color;

        double temp_result;

        int red, green, blue;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        double nextX, nextY;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        double start_val;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;



        for(y = FROMy; y < TOy; y++) {
            temp_y0 = temp_ycenter_size + y * temp_size_image_size_y;
            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {
                    temp_x0 = temp_xcenter_size + x * temp_size_image_size_x;
                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations_fast_julia[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                    color = start_val == max_iterations ? fractal_color : palette_color.getPaletteColor(start_val + color_cycling_location);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {
                        temp_result = fractal.calculateJulia(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                        color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    startColor = rgbs[pix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                        curPix = startPix = startPix - image_size;
                        iy--;
                    }

                    temp_ix = ix;
                    temp_iy = iy;

                    do {                                            // tracing cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];

                            nextX = temp_xcenter_size + next_ix * temp_size_image_size_x;
                            nextY = temp_ycenter_size + next_iy * temp_size_image_size_y;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                temp_result = image_iterations_fast_julia[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {
                                    temp_result = fractal.calculateJulia(new Complex(nextX + antialiasing_x[i], nextY + antialiasing_y[i]));
                                    color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                nextColor = rgbs[nextPix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                            }


                            if(nextColor == startColor) {
                                curDir = dir;
                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);


                    curDir = dirRight;


                    do {                                                 // 2nd cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];


                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if(rgbs[nextPix] == startColor) {           // flooding
                                curDir = dir;
                                if(dir == dirUP) {
                                    floodPix = curPix;
                                    flood_ix = temp_ix;

                                    while(true) {
                                        flood_ix++;

                                        if(flood_ix >= TOx) {
                                            break;
                                        }

                                        floodPix++;

                                        if((floodColor = rgbs[floodPix]) == culcColor) {
                                            rgbs[floodPix] = startColor;
                                            image_iterations_fast_julia[floodPix] = start_val;
                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }
                                }

                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);

                }
            }
        }
    }

    private void drawFastJuliaAntialiased(int image_size) {

        if(!boundary_tracing) {
            drawFastJuliaAntialiasedBruteForce(image_size);
        }
        else {
            drawFastJuliaAntialiasedBoundaryTracing(image_size);
        }

    }

    private void drawFastJuliaPolarAntialiased(int image_size) {

        if(!boundary_tracing) {
            drawFastJuliaPolarAntialiasedBruteForce(image_size);
        }
        else {
            drawFastJuliaPolarAntialiasedBoundaryTracing(image_size);
        }

    }

    private void drawFastJuliaPolarAntialiasedBruteForce(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        double temp_result;

        int x, y, loc;

        int condition = image_size * image_size;

        int color;

        int red, green, blue;

        double exp_x_antialiasing_size = Math.exp(x_antialiasing_size);
        double exp_inv_x_antialiasing_size = 1 / exp_x_antialiasing_size;

        double exp_x_antialiasing_size_x2 = exp_x_antialiasing_size * exp_x_antialiasing_size;
        double exp_inv_x_antialiasing_size_x2 = 1 / exp_x_antialiasing_size_x2;

        double antialiasing_x[] = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
            exp_inv_x_antialiasing_size, exp_x_antialiasing_size, 1, 1,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, 1, 1, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};


        double sin_y_antialiasing_size = Math.sin(y_antialiasing_size);
        double cos_y_antialiasing_size = Math.cos(y_antialiasing_size);

        double sin_inv_y_antialiasing_size = -sin_y_antialiasing_size;
        double cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        double sin_y_antialiasing_size_x2 = 2 * sin_y_antialiasing_size * cos_y_antialiasing_size;
        double cos_y_antialiasing_size_x2 = 2 * cos_y_antialiasing_size * cos_y_antialiasing_size - 1;

        double sin_inv_y_antialiasing_size_x2 = -sin_y_antialiasing_size_x2;
        double cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;


        double antialiasing_y_sin[] = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
            0, 0, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
            sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2,
            sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};


        double antialiasing_y_cos[] = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
            1, 1, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
            cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2,
            cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};

        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            temp_result = image_iterations_fast_julia[loc] = fractal.calculateJulia(new Complex(xcenter + r * cf, ycenter + r * sf));
            color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {

                sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                r2 = r * antialiasing_x[i];

                temp_result = fractal.calculateJulia(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

        } while(true);
    }

    private void drawFastJuliaPolarAntialiasedBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, r2, f2, sf2, cf2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        double temp_result;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;

        double temp_x0, temp_y0;


        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};

        double nextX, nextY, start_val;

        double sf3, cf3, r3;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};


        double exp_x_antialiasing_size = Math.exp(x_antialiasing_size);
        double exp_inv_x_antialiasing_size = 1 / exp_x_antialiasing_size;

        double exp_x_antialiasing_size_x2 = exp_x_antialiasing_size * exp_x_antialiasing_size;
        double exp_inv_x_antialiasing_size_x2 = 1 / exp_x_antialiasing_size_x2;

        double antialiasing_x[] = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
            exp_inv_x_antialiasing_size, exp_x_antialiasing_size, 1, 1,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, 1, 1, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};


        double sin_y_antialiasing_size = Math.sin(y_antialiasing_size);
        double cos_y_antialiasing_size = Math.cos(y_antialiasing_size);

        double sin_inv_y_antialiasing_size = -sin_y_antialiasing_size;
        double cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        double sin_y_antialiasing_size_x2 = 2 * sin_y_antialiasing_size * cos_y_antialiasing_size;
        double cos_y_antialiasing_size_x2 = 2 * cos_y_antialiasing_size * cos_y_antialiasing_size - 1;

        double sin_inv_y_antialiasing_size_x2 = -sin_y_antialiasing_size_x2;
        double cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;


        double antialiasing_y_sin[] = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
            0, 0, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
            sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2,
            sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};


        double antialiasing_y_cos[] = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
            1, 1, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
            cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2,
            cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};


        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;

        int red, green, blue, color;

        for(y = FROMy; y < TOy; y++) {

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {

                    r = Math.exp(x * mulx + start);

                    temp_x0 = xcenter + r * cf;
                    temp_y0 = ycenter + r * sf;

                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations_fast_julia[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                    color = start_val == max_iterations ? fractal_color : palette_color.getPaletteColor(start_val + color_cycling_location);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {

                        sf3 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                        cf3 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                        r3 = r * antialiasing_x[i];

                        temp_x0 = xcenter + r3 * cf3;
                        temp_y0 = ycenter + r3 * sf3;

                        temp_result = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                        color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    startColor = rgbs[pix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));


                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                        curPix = startPix = startPix - image_size;
                        iy--;
                    }

                    temp_ix = ix;
                    temp_iy = iy;

                    do {                                            // tracing cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];

                            f2 = next_iy * muly;
                            sf2 = Math.sin(f2);
                            cf2 = Math.cos(f2);

                            r2 = Math.exp(next_ix * mulx + start);

                            nextX = xcenter + r2 * cf2;
                            nextY = ycenter + r2 * sf2;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                temp_result = image_iterations_fast_julia[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {

                                    sf3 = sf2 * antialiasing_y_cos[i] + cf2 * antialiasing_y_sin[i];
                                    cf3 = cf2 * antialiasing_y_cos[i] - sf2 * antialiasing_y_sin[i];

                                    r3 = r2 * antialiasing_x[i];

                                    nextX = xcenter + r3 * cf3;
                                    nextY = ycenter + r3 * sf3;

                                    temp_result = fractal.calculateJulia(new Complex(nextX, nextY));
                                    color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                nextColor = rgbs[nextPix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));
                            }


                            if(nextColor == startColor) {
                                curDir = dir;
                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);


                    curDir = dirRight;


                    do {                                                 // 2nd cycle
                        for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                            dir = Dir & maskDir;
                            nextPix = curPix + delPix[dir];

                            next_ix = temp_ix + intX[dir];
                            next_iy = temp_iy + intY[dir];


                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if(rgbs[nextPix] == startColor) {           // flooding  
                                curDir = dir;
                                if(dir == dirUP) {
                                    floodPix = curPix;
                                    flood_ix = temp_ix;

                                    while(true) {
                                        flood_ix++;

                                        if(flood_ix >= TOx) {
                                            break;
                                        }

                                        floodPix++;

                                        if((floodColor = rgbs[floodPix]) == culcColor) {
                                            rgbs[floodPix] = startColor;
                                            image_iterations_fast_julia[floodPix] = start_val;
                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }
                                }

                                curPix = nextPix;
                                temp_ix = next_ix;
                                temp_iy = next_iy;
                                break;
                            }
                        }
                    } while(curPix != startPix);
                }
            }
        }

    }

    //1 Thread
    private void colorCycling() {

        if(!color_cycling) {
            return;
        }

        ptr.setWholeImageDone(false);

        int image_size = image.getHeight();

        color_cycling_location++;

        color_cycling_location = color_cycling_location > Integer.MAX_VALUE - 1 ? 0 : color_cycling_location;

        int condition = image_size * image_size;

        double temp_result;

        for(int loc = 0; loc < condition; loc++) {
            temp_result = image_iterations[loc];
            rgbs[loc] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
        }

        if(bump_map || fake_de) {
            applyPostProcessing(image_size);
        }

        ptr.setWholeImageDone(true);

        ptr.getMainPanel().repaint();

        try {
            Thread.sleep(175);
        }
        catch(InterruptedException ex) {
        }

        colorCycling();

    }

    private void rotate3DModel() {

        int image_size = image.getHeight();

        rotate(image_size, false);

        if(drawing_done != 0) {
            update(drawing_done);
        }

        int done = synchronization.incrementAndGet();


        if(done == ptr.getNumberOfThreads()) {
            applyFilters();

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.getMainPanel().repaint();
            ptr.getProgressBar().setValue((detail * detail) + (detail * detail / 100));
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }

    }
    
    private void applyPaletteAndFilter3DModel() {

        int image_size = image.getHeight();

        rotate(image_size, true);

        if(drawing_done != 0) {
            update(drawing_done);
        }

        int done = synchronization.incrementAndGet();

        if(done == ptr.getNumberOfThreads()) {
            applyFilters();

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.getMainPanel().repaint();
            ptr.getProgressBar().setValue((detail * detail) + (detail * detail / 100));
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }

    }

    private void applyPaletteAndFilter() {

        int image_size = image.getHeight();


        changePalette(image_size);


        if(drawing_done != 0) {
            update(drawing_done);
        }

        int done = synchronization.incrementAndGet();


        if(done == ptr.getNumberOfThreads()) {

            applyFilters();

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.getMainPanel().repaint();
            ptr.getProgressBar().setValue((image_size * image_size) + (image_size * image_size / 100));
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }

    }

    private void changePalette(int image_size) {

        int pixel_percent = image_size * image_size / 100;

        double temp_result;
        if(ptr.getJuliaMap()) {
            for(int y = FROMy; y < TOy; y++) {
                for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                    temp_result = image_iterations[loc];
                    rgbs[loc] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                    drawing_done++;
                }

                if(drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }

            }
        }
        else {
            int x = 0;
            int y = 0;
            boolean whole_area;
            int step;
            int total_drawing;

            double temp_starting_pixel_cicle;
            int temp_starting_pixel_color;

            int thread_size_width = TOx - FROMx;
            int thread_size_height = TOy - FROMy;

            int slice_FROMx;
            int slice_FROMy;
            int slice_TOx;
            int slice_TOy;

            int loc;

            for(int i = 0; i < thread_slices; i++) {
                slice_FROMy = FROMy + i * (thread_size_height) / thread_slices;
                slice_TOy = FROMy + (i + 1) * (thread_size_height) / thread_slices;
                for(int j = 0; j < thread_slices; j++) {
                    slice_FROMx = FROMx + j * (thread_size_width) / thread_slices;
                    slice_TOx = FROMx + (j + 1) * (thread_size_width) / thread_slices;


                    double temp = (slice_TOy - slice_FROMy + 1) * 0.5;

                    for(y = slice_FROMy, whole_area = true, step = 0, total_drawing = 0; step < temp; step++, whole_area = true) {

                        x = slice_FROMx + step;

                        loc = y * image_size + x;
                        temp_result = temp_starting_pixel_cicle = image_iterations[loc];

                        rgbs[loc] = temp_starting_pixel_color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                        drawing_done++;
                        total_drawing++;

                        for(x++, loc = y * image_size + x; x < slice_TOx - step; x++, loc++) {
                            temp_result = image_iterations[loc];
                            if(temp_result == temp_starting_pixel_cicle) {
                                rgbs[loc] = temp_starting_pixel_color;
                            }
                            else {
                                rgbs[loc] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
                                whole_area = false;
                            }

                            drawing_done++;
                            total_drawing++;

                        }

                        for(x--, y++; y < slice_TOy - step; y++) {
                            loc = y * image_size + x;
                            temp_result = image_iterations[loc];
                            if(temp_result == temp_starting_pixel_cicle) {
                                rgbs[loc] = temp_starting_pixel_color;
                            }
                            else {
                                rgbs[loc] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
                                whole_area = false;
                            }

                            drawing_done++;
                            total_drawing++;
                        }

                        for(y--, x--, loc = y * image_size + x; x >= slice_FROMx + step; x--, loc--) {
                            temp_result = image_iterations[loc];
                            if(temp_result == temp_starting_pixel_cicle) {
                                rgbs[loc] = temp_starting_pixel_color;
                            }
                            else {
                                rgbs[loc] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
                                whole_area = false;
                            }


                            drawing_done++;
                            total_drawing++;
                        }

                        for(x++, y--; y > slice_FROMy + step; y--) {
                            loc = y * image_size + x;
                            temp_result = image_iterations[loc];
                            if(temp_result == temp_starting_pixel_cicle) {
                                rgbs[loc] = temp_starting_pixel_color;
                            }
                            else {
                                rgbs[loc] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
                                whole_area = false;
                            }

                            drawing_done++;
                            total_drawing++;
                        }
                        y++;
                        x++;


                        if(drawing_done / pixel_percent >= 1) {
                            update(drawing_done);
                            drawing_done = 0;
                        }

                        if(whole_area) {
                            int temp6 = step + 1;
                            int temp1 = slice_TOx - temp6;
                            int temp2 = slice_TOy - temp6;

                            for(int k = y; k < temp2; k++) {
                                Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, temp_starting_pixel_color);
                            }

                            update((slice_TOx - slice_FROMx) * (slice_TOy - slice_FROMy) - total_drawing);
                            break;
                        }
                    }
                }
            }

        }

        if(bump_map || fake_de) {
            int sync2 = synchronization2.incrementAndGet();

            while(sync2 != ptr.getNumberOfThreads()) {
                yield();
                sync2 = synchronization2.get();
            }

            applyPostProcessing(image_size);
        }

    }

    private void drawJuliaMap() {

        int image_size = image.getHeight();


        if(bump_map || fake_de) {
            if(filters[MainWindow.ANTIALIASING]) {
                juliaMapAntialiasedPostProcess(image_size);
            }
            else {
                juliaMapPostProcess(image_size);
            }
        }
        else {
            if(filters[MainWindow.ANTIALIASING]) {
                juliaMapAntialiased(image_size);
            }
            else {
                juliaMap(image_size);
            }
        }



        if(drawing_done != 0) {
            update(drawing_done);
        }

        int done = synchronization.incrementAndGet();

        if(done == ptr.getJuliaMapSlices()) {

            applyFilters();


            ptr.updateValues("Julia Map mode");
            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            ptr.getProgressBar().setValue((image_size * image_size) + (image_size * image_size / 100));
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getJuliaMapSlices() + "</html>");
        }

    }

    private void drawJuliaMapPolar() {

        int image_size = image.getHeight();


        if(bump_map || fake_de) {
            if(filters[MainWindow.ANTIALIASING]) {
                juliaMapPolarAntialiasedPostProcess(image_size);
            }
            else {
                juliaMapPolarPostProcess(image_size);
            }
        }
        else {
            if(filters[MainWindow.ANTIALIASING]) {
                juliaMapPolarAntialiased(image_size);
            }
            else {
                juliaMapPolar(image_size);
            }
        }



        if(drawing_done != 0) {
            update(drawing_done);
        }

        int done = synchronization.incrementAndGet();

        if(done == ptr.getJuliaMapSlices()) {

            applyFilters();

            ptr.updateValues("Julia Map mode");
            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            ptr.getProgressBar().setValue((image_size * image_size) + (image_size * image_size / 100));
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getJuliaMapSlices() + "</html>");
        }

    }

    private void juliaMap(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / (TOx - FROMx);
        double temp_size_image_size_y = (size * height_ratio) / (TOx - FROMx);

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;

        int pixel_percent = image_size * image_size / 100;

        double temp_y0 = temp_ycenter_size;
        double temp_x0 = temp_xcenter_size;

        double temp_result;

        for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size_y) {
            for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size_x, loc++) {

                temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                rgbs[loc] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                drawing_done++;

            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

            temp_x0 = temp_xcenter_size;

        }

    }

    private void juliaMapPolar(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / (TOx - FROMx);

        double mulx = muly * height_ratio;

        start = center - mulx * (TOx - FROMx) / 2.0;

        double temp_result;

        for(int y = FROMy, y1 = 0; y < TOy; y++, y1++) {
            f = y1 * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);
            for(int x = FROMx, x1 = 0, loc = y * image_size + x; x < TOx; x++, loc++, x1++) {

                r = Math.exp(x1 * mulx + start);

                temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(xcenter + r * cf, ycenter + r * sf));
                rgbs[loc] = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                drawing_done++;

            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        }

    }

    private void juliaMapAntialiased(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / (TOx - FROMx);
        double temp_size_image_size_y = (size * height_ratio) / (TOx - FROMx);

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() - size_2_y;

        int pixel_percent = image_size * image_size / 100;

        double temp_y0 = temp_ycenter_size;
        double temp_x0 = temp_xcenter_size;

        double temp_result;

        int color;

        int red, green, blue;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;

        for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size_y) {
            for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size_x, loc++) {

                temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {
                    temp_result = fractal.calculateJulia(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                    color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                drawing_done++;
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

            temp_x0 = temp_xcenter_size;

        }

    }

    private void juliaMapPolarAntialiased(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r, cf2, sf2, r2;
        double muly = (2 * circle_period * Math.PI) / (TOx - FROMx);

        double mulx = muly * height_ratio;

        start = center - mulx * (TOx - FROMx) / 2.0;

        double temp_result;

        int color;

        int red, green, blue;

        double exp_x_antialiasing_size = Math.exp(x_antialiasing_size);
        double exp_inv_x_antialiasing_size = 1 / exp_x_antialiasing_size;

        double exp_x_antialiasing_size_x2 = exp_x_antialiasing_size * exp_x_antialiasing_size;
        double exp_inv_x_antialiasing_size_x2 = 1 / exp_x_antialiasing_size_x2;

        double antialiasing_x[] = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
            exp_inv_x_antialiasing_size, exp_x_antialiasing_size, 1, 1,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, 1, 1, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};


        double sin_y_antialiasing_size = Math.sin(y_antialiasing_size);
        double cos_y_antialiasing_size = Math.cos(y_antialiasing_size);

        double sin_inv_y_antialiasing_size = -sin_y_antialiasing_size;
        double cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        double sin_y_antialiasing_size_x2 = 2 * sin_y_antialiasing_size * cos_y_antialiasing_size;
        double cos_y_antialiasing_size_x2 = 2 * cos_y_antialiasing_size * cos_y_antialiasing_size - 1;

        double sin_inv_y_antialiasing_size_x2 = -sin_y_antialiasing_size_x2;
        double cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;


        double antialiasing_y_sin[] = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
            0, 0, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
            sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2,
            sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};


        double antialiasing_y_cos[] = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
            1, 1, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
            cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2,
            cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};

        double temp_samples = filters_options_vals[MainWindow.ANTIALIASING] + 1;

        for(int y = FROMy, y1 = 0; y < TOy; y++, y1++) {
            f = y1 * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);
            for(int x = FROMx, x1 = 0, loc = y * image_size + x; x < TOx; x++, loc++, x1++) {
                r = Math.exp(x1 * mulx + start);

                temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(xcenter + r * cf, ycenter + r * sf));
                color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for(int i = 0; i < filters_options_vals[MainWindow.ANTIALIASING]; i++) {
                    sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                    cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                    r2 = r * antialiasing_x[i];

                    temp_result = fractal.calculateJulia(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                    color = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                drawing_done++;
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        }

    }

    private void juliaMapPostProcess(int image_size) {

        juliaMap(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getJuliaMapSlices()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void juliaMapAntialiasedPostProcess(int image_size) {

        juliaMapAntialiased(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getJuliaMapSlices()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void juliaMapPolarPostProcess(int image_size) {

        juliaMapPolar(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getJuliaMapSlices()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    private void juliaMapPolarAntialiasedPostProcess(int image_size) {

        juliaMapPolarAntialiased(image_size);

        int sync2 = synchronization2.incrementAndGet();

        while(sync2 != ptr.getJuliaMapSlices()) {
            yield();
            sync2 = synchronization2.get();
        }

        applyPostProcessing(image_size);

    }

    /*
    visualize_planes
     * 
     * double size = 6;
    
    
    double size_2 = size * 0.5;
    double temp_xcenter_size = 0 - size_2;
    double temp_ycenter_size = 0 - size_2;
    
    //double temp_size_image_size = size / image_size;
    
    double temp_size_image_size = size / (0.6 * image_size);
    int d = (int)(image_size * 0.2);
    
    for(int x = FROMx; x < TOx; x++) {
    for(int y = FROMy; y < TOy; y++) {
    
    if(x >= 0.20 * image_size && x < 0.80 * image_size && y >= 0.20 * image_size && y < 0.80 * image_size) {
    int new_x = x - d;
    int new_y = y - d;
    
    Complex new_complex = fractal.getTransformedNumber(new Complex(temp_xcenter_size + new_x * temp_size_image_size, temp_ycenter_size + new_y * temp_size_image_size));
    
    int x0 = (int)((new_complex.getRe() - temp_xcenter_size) / temp_size_image_size + 0.5);
    int y0 = (int)((new_complex.getIm() - temp_ycenter_size) / temp_size_image_size + 0.5);
    
    if((x0 + d) >= 0 && (x0 + d) < image_size && (y0 + d) >= 0 && (y0 + d) < image_size) {
    rgbs[(y0 + d) * image_size + (x0 + d)] = fractal_color;
    }
    
    }
    }
    }
    
     */
    public void update(int new_percent) {

        ptr.getProgressBar().setValue(ptr.getProgressBar().getValue() + new_percent);

    }

    private void filterEmboss() {

        int image_size = image.getHeight();

        if(filters_options_vals[MainWindow.EMBOSS] == 1) {
            int kernelWidth = (int)Math.sqrt((double)EMBOSS.length);
            int kernelHeight = kernelWidth;
            int xOffset = (kernelWidth - 1) / 2;
            int yOffset = xOffset;

            BufferedImage newSource = new BufferedImage(image_size + kernelWidth - 1, image_size + kernelHeight - 1, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = newSource.createGraphics();
            graphics.drawImage(image, xOffset, yOffset, null);


            Kernel kernel = new Kernel(kernelWidth, kernelHeight, EMBOSS);
            ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            cop.filter(newSource, image);

            graphics.dispose();
            graphics = null;
            kernel = null;
            cop = null;
            newSource = null;
        }
        else {
            int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

            for(int i = image_size - 1; i >= 0; i--) {
                for(int j = image_size - 1, loc = i * image_size + j; j >= 0; j--, loc--) {
                    int current = raster[loc];

                    int upperLeft = 0;
                    if(i > 0 && j > 0) {
                        upperLeft = raster[loc - image_size - 1];
                    }

                    int rDiff = ((current >> 16) & 0xff) - ((upperLeft >> 16) & 0xff);
                    int gDiff = ((current >> 8) & 0xff) - ((upperLeft >> 8) & 0xff);
                    int bDiff = (current & 0xff) - (upperLeft & 0xff);

                    int diff = rDiff;
                    if(Math.abs(gDiff) > Math.abs(diff)) {
                        diff = gDiff;
                    }
                    if(Math.abs(bDiff) > Math.abs(diff)) {
                        diff = bDiff;
                    }

                    int grayLevel = Math.max(Math.min(128 + diff, 255), 0);
                    raster[loc] = 0xff000000 | ((grayLevel << 16) + (grayLevel << 8) + grayLevel);
                }
            }
        }

    }

    private void filterEdgeDetection() {

        /*float[] EDGES = {1.0f,   1.0f,  1.0f,
        1.0f, -8.0f,  1.0f,
        1.0f,   1.0f,  1.0f};*/

        /*float[] EDGES = {-7.0f,   -7.0f,  -7.0f,
        -7.0f, 56.0f,  -7.0f,
        -7.0f,   -7.0f,  -7.0f};*/

        /*float[] EDGES = {-1.0f, -1.0f, -2.0f, -1.0f, -1.0f,
        -1.0f, -2.0f, -4.0f, -2.0f, -1.0f,
        -2.0f, -4.0f, 44.0f, -4.0f, -2.0f,
        -1.0f, -2.0f, -4.0f, -2.0f, -1.0f,
        -1.0f, -1.0f, -2.0f, -1.0f, -1.0f};*/


        float[] EDGES = null;

        if(filters_options_vals[MainWindow.EDGE_DETECTION] == 1) {
            EDGES = thick_edges;
        }
        else {
            EDGES = thin_edges;
        }


        int kernelWidth = (int)Math.sqrt((double)EDGES.length);


        int image_size = image.getHeight();

        Kernel kernel = new Kernel(kernelWidth, kernelWidth, EDGES);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
        BufferedImage newSource = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
        BufferedImage newSource2 = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);

        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)newSource.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        cop.filter(newSource, newSource2);

        int black = Color.BLACK.getRGB();

        int[] raster = ((DataBufferInt)newSource2.getRaster().getDataBuffer()).getData();

        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            if((0xff000000 | raster[p]) == black) {
                rgbs[p] = black;
            }
        }

        kernel = null;
        cop = null;
        newSource = null;
        newSource2 = null;

    }

    private void filterSharpness() {

        int image_size = image.getHeight();

        float[] SHARPNESS = null;

        if(filters_options_vals[MainWindow.SHARPNESS] == 0) {
            SHARPNESS = sharpness_low;
        }
        else {
            SHARPNESS = sharpness_high;
        }

        int kernelWidth = (int)Math.sqrt((double)SHARPNESS.length);
        int kernelHeight = kernelWidth;
        int xOffset = (kernelWidth - 1) / 2;
        int yOffset = xOffset;



        BufferedImage newSource = new BufferedImage(image_size + kernelWidth - 1, image_size + kernelHeight - 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = newSource.createGraphics();
        graphics.drawImage(image, xOffset, yOffset, null);


        Kernel kernel = new Kernel(kernelWidth, kernelHeight, SHARPNESS);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        cop.filter(newSource, image);

        graphics.dispose();
        graphics = null;
        kernel = null;
        cop = null;
        newSource = null;
    }

    private void filterBlurring() { //OLD antialiasing method (blurring)

        /*     float b = 0.05f;
        float a = 1.0f - (8.0f * b);
        
        
        float[] AA = {b, b, b,    // low-pass filter
        b, a, b,
        b, b, b};
        
        
        /* float c = 0.00390625f;
        float b = 0.046875f;
        float a = 1.0f - (16.0f * c + 8.0f * b);
        
        float[] AA = {c, c, c, c, c,    // low-pass filter
        c, b, b, b, c,
        c, b, a, b, c,
        c, b, b, b, c,
        c, c, c, c, c};*/




        /*  float d = 1.0f / 3096.0f;
        float c = 12.0f * d;
        float b = 12.0f * c;
        float a = 1.0f - (24.0f * d + 16.0f * c + 8.0f * b);
        
        float[] AA = {d, d, d, d, d, d, d,    // low-pass filter
        d, c, c, c, c, c, d,
        d, c, b, b, b, c, d,
        d, c, b, a, b, c, d,
        d, c, b, b, b, c, d,
        d, c, c, c, c, c, d,
        d, d, d, d, d, d, d};*/




        float[] blur = null;

        /*if(blurring == 1) {
        float[] MOTION_BLUR = {0.2f,  0.0f,  0.0f, 0.0f,  0.0f,
        0.0f,   0.2f,  0.0f, 0.0f,  0.0f,
        0.0f,   0.0f,  0.2f, 0.0f,  0.0f,
        0.0f,   0.0f,  0.0f, 0.2f,  0.0f,
        0.0f,   0.0f,  0.0f, 0.0f,  0.2f};
        
        blur = MOTION_BLUR;
        }*/
        //else {
        float e = 1.0f / 37184.0f;
        float d = 12.0f * e;
        float c = 12.0f * d;
        float b = 12.0f * c;
        float a = 1.0f - (32.0f * e + 24.0f * d + 16.0f * c + 8.0f * b);

        float[] NORMAL_BLUR = {e, e, e, e, e, e, e, e, e, // low-pass filter
            e, d, d, d, d, d, d, d, e,
            e, d, c, c, c, c, c, d, e,
            e, d, c, b, b, b, c, d, e,
            e, d, c, b, a, b, c, d, e,
            e, d, c, b, b, b, c, d, e,
            e, d, c, c, c, c, c, d, e,
            e, d, d, d, d, d, d, d, e,
            e, e, e, e, e, e, e, e, e};

        blur = NORMAL_BLUR;
        // }


        /*float h = 1.0f / 64260344.0f;
        float g = 12.0f * h;
        float f = 12.0f * g;
        float e = 12.0f * f;
        float d = 12.0f * e;
        float c = 12.0f * d;
        float b = 12.0f * c;
        float a = 1.0f - (56.0f * h + 48.0f * g + 40.0f * f + 32.0f * e + 24.0f * d + 16.0f * c + 8.0f * b);
        
        float[] AA  = {h, h, h, h, h, h, h, h, h, h, h, h, h, h, h,    // low-pass filter
        h, g, g, g, g, g, g, g, g, g, g, g, g, g, h,
        h, g, f, f, f, f, f, f, f, f, f, f, f, g, h,
        h, g, f, e, e, e, e, e, e, e, e, e, f, g, h,
        h, g, f, e, d, d, d, d, d, d, d, e, f, g, h,
        h, g, f, e, d, c, c, c, c, c, d, e, f, g, h,
        h, g, f, e, d, c, b, b, b, c, d, e, f, g, h,
        h, g, f, e, d, c, b, a, b, c, d, e, f, g, h,
        h, g, f, e, d, c, b, b, b, c, d, e, f, g, h,
        h, g, f, e, d, c, c, c, c, c, d, e, f, g, h,
        h, g, f, e, d, d, d, d, d, d, d, e, f, g, h,
        h, g, f, e, e, e, e, e, e, e, e, e, f, g, h,
        h, g, f, f, f, f, f, f, f, f, f, f, f, g, h,
        h, g, g, g, g, g, g, g, g, g, g, g, g, g, h,
        h, h, h, h, h, h, h, h, h, h, h, h, h, h, h};*/


        //resize the picture to cover the image edges
        int kernelWidth = (int)Math.sqrt((double)blur.length);
        int kernelHeight = kernelWidth;
        int xOffset = (kernelWidth - 1) / 2;
        int yOffset = xOffset;

        int image_size = image.getHeight();

        BufferedImage newSource = new BufferedImage(image_size + kernelWidth - 1, image_size + kernelHeight - 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = newSource.createGraphics();
        graphics.drawImage(image, xOffset, yOffset, null);


        Kernel kernel = new Kernel(kernelWidth, kernelHeight, blur);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        cop.filter(newSource, image);

        graphics.dispose();
        graphics = null;
        blur = null;
        kernel = null;
        cop = null;
        newSource = null;
    }

    private void filterInvertColors() {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int condition = image_size * image_size;

        int r, g, b;

        for(int p = 0; p < condition; p++) {

            if(filters_options_vals[MainWindow.INVERT_COLORS] == 0) {
                raster[p] = ~(raster[p] & 0x00ffffff);
            }
            else {
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                float res[] = new float[3];

                Color.RGBtoHSB(r, g, b, res);

                raster[p] = Color.HSBtoRGB(res[0], res[1], 1.0f - res[2]);
            }

        }

    }

    private void filterMaskColors() {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int mask = 0;

        if(filters_options_vals[MainWindow.COLOR_CHANNEL_MASKING] == 0) {
            mask = 0xff00ffff;  //RED
        }
        else if(filters_options_vals[MainWindow.COLOR_CHANNEL_MASKING] == 1) {
            mask = 0xffff00ff;  //GREEN
        }
        else {
            mask = 0xffffff00;  //BLUE
        }


        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            raster[p] = raster[p] & mask;
        }

    }

    private void filterFadeOut() {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        int r, g, b;

        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            r = (r + 255) >> 1;
            g = (g + 255) >> 1;
            b = (b + 255) >> 1;

            raster[p] = 0xff000000 | (r << 16) | (g << 8) | b;
        }

    }

    private void filterColorChannelSwapping() {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        int r, g, b;

        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            if(filters_options_vals[MainWindow.COLOR_CHANNEL_SWAPPING] == 0) {
                raster[p] = 0xff000000 | (r << 16) | (b << 8) | g;
            }
            else if(filters_options_vals[MainWindow.COLOR_CHANNEL_SWAPPING] == 1) {
                raster[p] = 0xff000000 | (g << 16) | (r << 8) | b;
            }
            else if(filters_options_vals[MainWindow.COLOR_CHANNEL_SWAPPING] == 2) {
                raster[p] = 0xff000000 | (g << 16) | (b << 8) | r;
            }
            else if(filters_options_vals[MainWindow.COLOR_CHANNEL_SWAPPING] == 3) {
                raster[p] = 0xff000000 | (b << 16) | (g << 8) | r;
            }
            else {
                raster[p] = 0xff000000 | (b << 16) | (r << 8) | g;
            }
        }

    }

    private void filterContrastBrightness() {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        float brightness = 1.0f;
        float contrast = 1.0f;

        if(filters_options_vals[MainWindow.CONTRAST_BRIGHTNESS] == 0) {
            contrast = 2.0f;
        }
        else if(filters_options_vals[MainWindow.CONTRAST_BRIGHTNESS] == 1) {
            brightness = 2.0f;
        }
        else {
            brightness = 2.0f;
            contrast = 2.0f;
        }


        int[] table = new int[256];
        float f;

        for(int i = 0; i < 256; i++) {
            f = i / 255.0f;
            f = f * brightness;
            f = (f - 0.5f) * contrast + 0.5f;
            table[i] = clamp((int)(255 * f));
        }

        int r, g, b;

        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            raster[p] = 0xff000000 | (table[r] << 16) | (table[g] << 8) | table[b];
        }

    }

    private void filterColorTemperature() {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();


        float rFactor = 0;
        float gFactor = 0;
        float bFactor = 0;

        switch (filters_options_vals[MainWindow.COLOR_TEMPERATURE]) {
            case 2000:
                rFactor = 1.0f;
                gFactor = 0.2484f;
                bFactor = 0.0061f;
                break;
            case 3000:
                rFactor = 1.0f;
                gFactor = 0.4589f;
                bFactor = 0.1483f;
                break;
            case 4000:
                rFactor = 1.0f;
                gFactor = 0.6354f;
                bFactor = 0.3684f;
                break;
            case 5000:
                rFactor = 1.0f;
                gFactor = 0.7792f;
                bFactor = 0.618f;
                break;
            case 10000:
                rFactor = 0.6033f;
                gFactor = 0.7106f;
                bFactor = 1.0f;
                break;
            case 30000:
                rFactor = 0.3471f;
                gFactor = 0.5188f;
                bFactor = 1.0f;
                break;
        }





        //  int t = 3 * (int)((((float)filters_options_vals[MainWindow.COLOR_TEMPATURE]) - 1000F) / 100F);
        //rFactor = blackBodyRGB[t];
        //gFactor = blackBodyRGB[t + 1];
        //bFactor = blackBodyRGB[t + 2];

        int r, g, b;

        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            r = (int)(r * rFactor + 0.5);
            g = (int)(g * gFactor + 0.5);
            b = (int)(b * bFactor + 0.5);

            raster[p] = 0xff000000 | (r << 16) | (g << 8) | b;
        }

    }

    private void filterGrayscale() {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int r, g, b, rgb;

        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            if(filters_options_vals[MainWindow.GRAYSCALE] == 0) {
                rgb = (r * 612 + g * 1202 + b * 233) >> 11;	// NTSC luma
                //rgb = (int)(r * 0.299F + g * 0.587F + b * 0.114F);	// NTSC luma
            }
            else if(filters_options_vals[MainWindow.GRAYSCALE] == 1) {
                rgb = (r * 435 + g * 1464 + b * 147) >> 11;	// HDTV luma
                //rgb = (int)(r * 0.2126F + g * 0.7152F + b * 0.0722F);	// HDTV luma
            }
            else if(filters_options_vals[MainWindow.GRAYSCALE] == 2) {
                rgb = (int)((r + g + b) / 3.0 + 0.5);	// simple average
            }
            else {
                rgb = (int)((Math.max(Math.max(r, g), b) + Math.min(Math.min(r, g), b)) / 2.0 + 0.5);
            }

            raster[p] = 0xff000000 | (rgb << 16) | (rgb << 8) | rgb;
        }

    }

    private void histogramEqualization() {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int condition = image_size * image_size;

        /*int r, g, b;
        
        int cdf[] = new int[100001];
        
        for(int p = 0; p < condition; p++) {
        r = (raster[p] >> 16) & 0xff;
        g = (raster[p] >> 8) & 0xff;
        b = raster[p] & 0xff;
        
        float res[] = new float[3];
        
        Color.RGBtoHSB(r, g, b, res);
        
        cdf[(int)(res[2] * 100000 + 0.5)]++;
        }
        
        int min = 0;
        int j = 0;
        while(min == 0) {
        min = cdf[j++];
        }
        int d = condition - min;
        
        for(int i = 1; i < cdf.length; i++) {
        cdf[i] += cdf[i - 1];
        }
        
        for(int p = 0; p < condition; p++) {
        r = (raster[p] >> 16) & 0xff;
        g = (raster[p] >> 8) & 0xff;
        b = raster[p] & 0xff;
        
        float res[] = new float[3];
        
        Color.RGBtoHSB(r, g, b, res);
        
        double temp = ((double)(cdf[(int)(res[2] * 100000 + 0.5)] - min)) / d;
        
        raster[p] = Color.HSBtoRGB(res[0], res[1], (float)(temp < 0 ? 0 : temp));
        
        
        }*/


        if(filters_options_vals[MainWindow.HISTOGRAM_EQUALIZATION] == 1) {

            int hist[][] = new int[3][256];

            int i, c;
            double mult, count, percentage, next_percentage;
            int low = 0, high = 0;

            // Fill the histogram by counting the number of pixels with each value
            for(int p = 0; p < condition; p++) {
                hist[0][(raster[p] >> 16) & 0xff]++;
                hist[1][(raster[p] >> 8) & 0xff]++;
                hist[2][raster[p] & 0xff]++;
            }

            count = image_size * image_size;
            // Fore each channel
            for(c = 0; c < hist.length; c++) {
                // Determine the low input value
                next_percentage = hist[c][0] / count;
                for(i = 0; i < 255; i++) {
                    percentage = next_percentage;
                    next_percentage += hist[c][i + 1] / count;
                    if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                        //low = i;//i+1; This is a deviation from the way The GIMP does it
                        low = i + 1;
                        // that prevents any change in the image if it's
                        // already optimal
                        break;
                    }
                }
                // Determine the high input value
                next_percentage = hist[c][255] / count;
                for(i = 255; i > 0; i--) {
                    percentage = next_percentage;
                    next_percentage += hist[c][i - 1] / count;
                    if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                        //high = i;//i-1; This is a deviation from the way The GIMP does it
                        high = i - 1;
                        // that prevents any change in the image if it's
                        // already optimal
                        break;
                    }
                }

                // Turn the histogram into a look up table to stretch the values
                mult = 255.0 / (high - low);

                for(i = 0; i < low; i++) {
                    hist[c][i] = 0;
                }

                for(i = 255; i > high; i--) {
                    hist[c][i] = 255;
                }

                double base = 0.0;

                for(i = low; i <= high; i++) {
                    hist[c][i] = (int)(base + 0.5);
                    base += mult;
                }
            }

            // Now apply the changes (stretch the values)

            int r, g, b;

            for(int p = 0; p < condition; p++) {
                r = hist[0][(raster[p] >> 16) & 0xff];
                g = hist[1][(raster[p] >> 8) & 0xff];
                b = hist[2][raster[p] & 0xff];

                raster[p] = 0xff000000 | (r << 16) | (g << 8) | b;
            }
        }
        else if(filters_options_vals[MainWindow.HISTOGRAM_EQUALIZATION] == 0) {
            int hist[] = new int[1025];

            int i;
            double mult, count, percentage, next_percentage;
            int low = 0, high = 0;

            int hist_len = hist.length - 1;

            int r, g, b;

            // Fill the histogram by counting the number of pixels with each value
            for(int p = 0; p < condition; p++) {
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                float res[] = new float[3];

                Color.RGBtoHSB(r, g, b, res);

                hist[(int)(res[2] * hist_len + 0.5)]++;
            }

            count = image_size * image_size;

            // Determine the low input value
            next_percentage = hist[0] / count;
            for(i = 0; i < hist_len; i++) {
                percentage = next_percentage;
                next_percentage += hist[i + 1] / count;
                if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                    //low = i;//i+1; This is a deviation from the way The GIMP does it
                    low = i + 1;
                    // that prevents any change in the image if it's
                    // already optimal
                    break;
                }
            }
            // Determine the high input value
            next_percentage = hist[hist_len] / count;
            for(i = hist_len; i > 0; i--) {
                percentage = next_percentage;
                next_percentage += hist[i - 1] / count;
                if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                    //high = i;//i-1; This is a deviation from the way The GIMP does it
                    high = i - 1;
                    // that prevents any change in the image if it's
                    // already optimal
                    break;
                }
            }

            // Turn the histogram into a look up table to stretch the values
            mult = ((double)hist_len) / (high - low);

            for(i = 0; i < low; i++) {
                hist[i] = 0;
            }

            for(i = hist_len; i > high; i--) {
                hist[i] = hist_len;
            }

            double base = 0.0;

            for(i = low; i <= high; i++) {
                hist[i] = (int)(base + 0.5);
                base += mult;
            }


            // Now apply the changes (stretch the values)

            for(int p = 0; p < condition; p++) {
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                float res[] = new float[3];

                Color.RGBtoHSB(r, g, b, res);

                double temp = hist[(int)(res[2] * hist_len + 0.5)] / ((double)hist_len);

                raster[p] = Color.HSBtoRGB(res[0], res[1], (float)temp);
            }

        }
        else if(filters_options_vals[MainWindow.HISTOGRAM_EQUALIZATION] == 2 || filters_options_vals[MainWindow.HISTOGRAM_EQUALIZATION] == 3 || filters_options_vals[MainWindow.HISTOGRAM_EQUALIZATION] == 4) {
            int hist[] = new int[256];

            int i;
            double mult, count, percentage, next_percentage;
            int low = 0, high = 0;

            // Fill the histogram by counting the number of pixels with each value
            for(int p = 0; p < condition; p++) {
                if(filters_options_vals[MainWindow.HISTOGRAM_EQUALIZATION] == 2) {
                    hist[(raster[p] >> 16) & 0xff]++;
                }
                else if(filters_options_vals[MainWindow.HISTOGRAM_EQUALIZATION] == 3) {
                    hist[(raster[p] >> 8) & 0xff]++;
                }
                else {
                    hist[raster[p] & 0xff]++;
                }
            }

            count = image_size * image_size;
            // Determine the low input value
            next_percentage = hist[0] / count;
            for(i = 0; i < 255; i++) {
                percentage = next_percentage;
                next_percentage += hist[i + 1] / count;
                if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                    //low = i;//i+1; This is a deviation from the way The GIMP does it
                    low = i + 1;
                    // that prevents any change in the image if it's
                    // already optimal
                    break;
                }
            }
            // Determine the high input value
            next_percentage = hist[255] / count;
            for(i = 255; i > 0; i--) {
                percentage = next_percentage;
                next_percentage += hist[i - 1] / count;
                if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                    //high = i;//i-1; This is a deviation from the way The GIMP does it
                    high = i - 1;
                    // that prevents any change in the image if it's
                    // already optimal
                    break;
                }
            }

            // Turn the histogram into a look up table to stretch the values
            mult = 255.0 / (high - low);

            for(i = 0; i < low; i++) {
                hist[i] = 0;
            }

            for(i = 255; i > high; i--) {
                hist[i] = 255;
            }

            double base = 0.0;

            for(i = low; i <= high; i++) {
                hist[i] = (int)(base + 0.5);
                base += mult;
            }


            // Now apply the changes (stretch the values)

            int r, g, b;

            for(int p = 0; p < condition; p++) {

                if(filters_options_vals[MainWindow.HISTOGRAM_EQUALIZATION] == 2) {
                    r = hist[(raster[p] >> 16) & 0xff];
                    g = (raster[p] >> 8) & 0xff;
                    b = raster[p] & 0xff;
                }
                else if(filters_options_vals[MainWindow.HISTOGRAM_EQUALIZATION] == 3) {
                    r = (raster[p] >> 16) & 0xff;
                    g = hist[(raster[p] >> 8) & 0xff];
                    b = raster[p] & 0xff;
                }
                else {
                    r = (raster[p] >> 16) & 0xff;
                    g = (raster[p] >> 8) & 0xff;
                    b = hist[raster[p] & 0xff];
                }

                raster[p] = 0xff000000 | (r << 16) | (g << 8) | b;
            }
        }
    }

    private void filterColorChannelMixing() {


        int[] matrix = {
            1, 0, 0,
            0, 1, 0,
            0, 0, 1,};

        matrix[filters_options_vals[MainWindow.COLOR_CHANNEL_MIXING]] = 1;

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int r, g, b;

        int condition = image_size * image_size;

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            r = clamp(matrix[0] * r + matrix[1] * g + matrix[2] * b);
            g = clamp(matrix[3] * r + matrix[4] * g + matrix[5] * b);
            b = clamp(matrix[6] * r + matrix[7] * g + matrix[8] * b);

            raster[p] = 0xff000000 | (r << 16) | (g << 8) | b;
        }

    }

    private void filterPosterize() {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int condition = image_size * image_size;

        int[] levels = new int[256];

        int numLevels = filters_options_vals[MainWindow.POSTERIZE];

        for(int i = 0; i < levels.length; i++) {
            levels[i] = 255 * (numLevels * i / 256) / (numLevels - 1);
        }

        int r, g, b;

        for(int p = 0; p < condition; p++) {
            r = levels[(raster[p] >> 16) & 0xff];
            g = levels[(raster[p] >> 8) & 0xff];
            b = levels[raster[p] & 0xff];

            raster[p] = 0xff000000 | (r << 16) | (g << 8) | b;
        }

    }

    private void filterSolarize() {

        int image_size = image.getHeight();

        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int condition = image_size * image_size;

        int[] table = new int[256];

        float f;

        for(int i = 0; i < table.length; i++) {
            f = i / 255.0f;
            f = f > 0.5f ? 2 * (f - 0.5f) : 2 * (0.5f - f);
            table[i] = clamp((int)(255 * f));
        }

        int r, g, b;

        for(int p = 0; p < condition; p++) {
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            raster[p] = 0xff000000 | (table[r] << 16) | (table[g] << 8) | table[b];
        }
    }

    private double getGradientX(double[] image_iterations, int index, int size) {
        
        int x = index % size;
        double it = image_iterations[index];
        
        if(x == 0) {
            return (image_iterations[index + 1] - it) * 2;
        }
        else if(x == size - 1) {
            return (it - image_iterations[index - 1]) * 2;
        }
        else {
            double diffL = it - image_iterations[index - 1];
            double diffR = it - image_iterations[index + 1];
            return diffL * diffR >= 0 ? 0 : diffL - diffR;
        }
        
    }

    private double getGradientY(double[] image_iterations, int index, int size) {
        
        int y = index / size;
        double it = image_iterations[index];
        
        if(y == 0) {
            return (it - image_iterations[index + size]) * 2;
        }
        else if(y == size - 1) {
            return (image_iterations[index - size] - it) * 2;
        }
        else {
            double diffU = it - image_iterations[index - size];
            double diffD = it - image_iterations[index + size];
            return diffD * diffU >= 0 ? 0 : diffD - diffU;
        }
        
    }

    private int changeBrightnessOfColor(int rgb, double delta) {
        if(delta > 0) {
            rgb ^= 0xFFFFFF;
            int r = rgb & 0xFF0000;
            int g = rgb & 0x00FF00;
            int b = rgb & 0x0000FF;
            double mul = (1.5 / (delta + 1.5));
            int ret = (int)(r * mul + 0.5) & 0xFF0000 | (int)(g * mul + 0.5) & 0x00FF00 | (int)(b * mul + 0.5);
            
            return 0xff000000 | (ret ^ 0xFFFFFF);
        }
        else {
            int r = rgb & 0xFF0000;
            int g = rgb & 0x00FF00;
            int b = rgb & 0x0000FF;
            double mul = (1.5 / (-delta + 1.5));
            //double mul = (Math.atan(-Math.abs(delta))*0.63662+1);
	    //double mul = Math.pow(2, -Math.abs(delta));
            int ret = 0xff000000 | (int)(r * mul + 0.5) & 0xFF0000 | (int)(g * mul + 0.5) & 0x00FF00 | (int)(b * mul + 0.5);
            
            return ret;
        }
    }

    public void setColorCycling(boolean temp) {

        color_cycling = temp;

    }

    public int getColorCyclingLocation() {

        return color_cycling_location;

    }

    private void applyFilters() {

        if(filters[MainWindow.COLOR_CHANNEL_SWAPPING]) {
            filterColorChannelSwapping();
        }

        if(filters[MainWindow.COLOR_CHANNEL_MIXING]) {
            filterColorChannelMixing();
        }

        if(filters[MainWindow.GRAYSCALE]) {
            filterGrayscale();
        }

        if(filters[MainWindow.POSTERIZE]) {
            filterPosterize();
        }

        if(filters[MainWindow.INVERT_COLORS]) {
            filterInvertColors();
        }

        if(filters[MainWindow.SOLARIZE]) {
            filterSolarize();
        }

        if(filters[MainWindow.COLOR_TEMPERATURE]) {
            filterColorTemperature();
        }

        if(filters[MainWindow.CONTRAST_BRIGHTNESS]) {
            filterContrastBrightness();
        }

        if(filters[MainWindow.HISTOGRAM_EQUALIZATION]) {
            histogramEqualization();
        }

        if(filters[MainWindow.COLOR_CHANNEL_MASKING]) {
            filterMaskColors();
        }

        if(filters[MainWindow.EDGE_DETECTION]) {
            filterEdgeDetection();
        }

        if(filters[MainWindow.SHARPNESS]) {
            filterSharpness();
        }

        if(filters[MainWindow.BLURRING]) {
            filterBlurring();
        }

        if(filters[MainWindow.EMBOSS]) {
            filterEmboss();
        }

        if(filters[MainWindow.FADE_OUT]) {
            filterFadeOut();
        }

    }

    private int clamp(int c) {

        if(c < 0) {
            return 0;
        }
        if(c > 255) {
            return 255;
        }
        return c;

    }

    public static void setArrays(int image_size) {

        vert = null;
        vert1 = null;
        Norm = null;
        Norm1z = null;
        image_iterations = new double[image_size * image_size];
        image_iterations_fast_julia = new double[MainWindow.FAST_JULIA_IMAGE_SIZE * MainWindow.FAST_JULIA_IMAGE_SIZE];

    }

    public static void set3DArrays(int detail) {

        image_iterations = null;
        image_iterations_fast_julia = null;
        vert = new float[detail][detail][4];
        vert1 = new float[detail][detail][2];
        Norm = new float[detail][detail][2][3];
        Norm1z = new float[detail][detail][2];

    }

    public static void resetAtomics() {

        synchronization = new AtomicInteger(0);
        total_calculated = new AtomicInteger(0);
        synchronization2 = new AtomicInteger(0);
        synchronization3 = new AtomicInteger(0);
        normal_drawing_algorithm_pixel = new AtomicInteger(0);

    }
}
