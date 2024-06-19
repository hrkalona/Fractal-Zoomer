
package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.awt.*;
import java.io.Serializable;

/**
 *
 * @author hrkalona2
 */
public class SettingsFractals1072 extends SettingsFractals1071 implements Serializable {
    private static final long serialVersionUID = 51234424L;
    private int[] direct_palette;
    private int[] direct_palette2;
    private Color trapColor1;
    private Color trapColor2;
    private Color trapColor3;
    private double trapColorInterpolation;
    private boolean trapIncludeNotEscaped;
    private boolean trapIncludeEscaped;
    private double pendulumHeight;
    private double[][] magnetLocation;
    private double[][] magnetStrength;
    private double[] pendulum;
    private double pendulumStepsize;
    private double[] friction;
    private double[] gravity;
    private double alpha;
    private double beta;
    private double delta;
    private double epsilon;
    private double[] durand_kerner_init_val;
    private double[] coefficients_im;
    private String lyapunovA;
    private String lyapunovB;
    private String lyapunovC;
    private String lyapunovD;
    private String lyapunovExpression;
    private boolean useLyapunovExponent;
    private boolean statisticIncludeEscaped;
    private boolean statisticIncludeNotEscaped;
    
    public SettingsFractals1072(Settings s) {
        
        super(s);
        direct_palette = s.ps.direct_palette;
        direct_palette2 = s.ps2.direct_palette;
        
        trapColor1 = s.pps.ots.trapColor1;
        trapColor2 = s.pps.ots.trapColor2;
        trapColor3 = s.pps.ots.trapColor3;
        trapColorInterpolation = s.pps.ots.trapColorInterpolation;
        trapIncludeNotEscaped = s.pps.ots.trapIncludeNotEscaped;
        trapIncludeEscaped = s.pps.ots.trapIncludeEscaped;
        
        pendulumHeight = s.fns.mps.height;
        magnetLocation = s.fns.mps.magnetLocation;
        magnetStrength = s.fns.mps.magnetStrength;
        pendulum = s.fns.mps.pendulum;
        pendulumStepsize = s.fns.mps.stepsize;
        friction = s.fns.mps.friction;
        gravity = s.fns.mps.gravity;
        
        alpha = s.fns.gcs.alpha;
        beta = s.fns.gcs.beta;
        delta = s.fns.gcs.delta;
        epsilon = s.fns.gcs.epsilon;
        
        durand_kerner_init_val = s.fns.durand_kerner_init_val;
        
        coefficients_im = s.fns.coefficients_im;
        
        lyapunovA = s.fns.lpns.lyapunovA;
        lyapunovB = s.fns.lpns.lyapunovB;
        lyapunovC = s.fns.lpns.lyapunovC;
        lyapunovD = s.fns.lpns.lyapunovD;
        lyapunovExpression = s.fns.lpns.lyapunovExpression;
        useLyapunovExponent = s.fns.lpns.useLyapunovExponent;
        
        statisticIncludeEscaped = s.pps.sts.statisticIncludeEscaped;
        statisticIncludeNotEscaped = s.pps.sts.statisticIncludeNotEscaped;
        
    }
    
    @Override
    public int getVersion() {

        return 1072;

    }

    @Override
    public boolean isJulia() {

        return false;

    }
    
    /**
     * @return the direct_palette
     */
    public int[] getDirectPalette() {
        return direct_palette;
    }

    /**
     * @return the direct_palette2
     */
    public int[] getDirectPalette2() {
        return direct_palette2;
    }

    /**
     * @return the trapColor1
     */
    public Color getTrapColor1() {
        return trapColor1;
    }

    /**
     * @return the trapColor2
     */
    public Color getTrapColor2() {
        return trapColor2;
    }

    /**
     * @return the trapColor3
     */
    public Color getTrapColor3() {
        return trapColor3;
    }

    /**
     * @return the trapColorInterpolation
     */
    public double getTrapColorInterpolation() {
        return trapColorInterpolation;
    }

    /**
     * @return the trapIncludeNotEscaped
     */
    public boolean getTrapIncludeNotEscaped() {
        return trapIncludeNotEscaped;
    }

    /**
     * @return the trapIncludeEscaped
     */
    public boolean getTrapIncludeEscaped() {
        return trapIncludeEscaped;
    }

    /**
     * @return the pendulumHeight
     */
    public double getPendulumHeight() {
        return pendulumHeight;
    }

    /**
     * @return the magnetLocation
     */
    public double[][] getMagnetLocation() {
        return magnetLocation;
    }

    /**
     * @return the magnetStrength
     */
    public double[][] getMagnetStrength() {
        return magnetStrength;
    }

    /**
     * @return the pendulum
     */
    public double[] getPendulum() {
        return pendulum;
    }

    /**
     * @return the pendulumStepsize
     */
    public double getPendulumStepsize() {
        return pendulumStepsize;
    }

    /**
     * @return the friction
     */
    public double[] getFriction() {
        return friction;
    }

    /**
     * @return the gravity
     */
    public double[] getGravity() {
        return gravity;
    }

    /**
     * @return the alpha
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * @return the beta
     */
    public double getBeta() {
        return beta;
    }

    /**
     * @return the delta
     */
    public double getDelta() {
        return delta;
    }

    /**
     * @return the epsilon
     */
    public double getEpsilon() {
        return epsilon;
    }

    /**
     * @return the durand_kerner_init_val
     */
    public double[] getDurandKernerInitVal() {
        return durand_kerner_init_val;
    }

    /**
     * @return the coefficients_im
     */
    public double[] getCoefficientsIm() {
        return coefficients_im;
    }

    /**
     * @return the lyapunovA
     */
    public String getLyapunovA() {
        return lyapunovA;
    }

    /**
     * @return the lyapunovB
     */
    public String getLyapunovB() {
        return lyapunovB;
    }

    /**
     * @return the lyapunovC
     */
    public String getLyapunovC() {
        return lyapunovC;
    }

    /**
     * @return the lyapunovD
     */
    public String getLyapunovD() {
        return lyapunovD;
    }

    /**
     * @return the lyapunovExpression
     */
    public String getLyapunovExpression() {
        return lyapunovExpression;
    }

    /**
     * @return the useLyapunovExponent
     */
    public boolean getUseLyapunovExponent() {
        return useLyapunovExponent;
    }

    /**
     * @return the statisticIncludeEscaped
     */
    public boolean getStatisticIncludeEscaped() {
        return statisticIncludeEscaped;
    }

    /**
     * @return the statisticIncludeNotEscaped
     */
    public boolean getStatisticIncludeNotEscaped() {
        return statisticIncludeNotEscaped;
    }
    
}
