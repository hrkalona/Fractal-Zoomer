/*
 * Copyright (C) 2020 hrkalona
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
package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

/**
 *
 * @author hrkalona
 */
public class SettingsFractals1074 extends SettingsFractals1073 implements Serializable {
    private static final long serialVersionUID = 974823410389235L;
    private boolean trueColorOut;
    private boolean trueColorIn;
    private int trueColorOutMode;
    private int trueColorInMode;
    private int trueColorOutPreset;
    private int trueColorInPreset;
    private String outTcComponent1;
    private String outTcComponent2;
    private String outTcComponent3;
    private String inTcComponent1;
    private String inTcComponent2;
    private String inTcComponent3;
    private int outTcColorSpace;
    private int inTcColorSpace;
    private double[] newton_hines_k;
    private boolean showAtomDomains;
    private int reductionFunction;
    private boolean useIterations;
    private boolean useSmoothing;
    private double stepsize_im;
    private String lyapunovInitialValue;
    
    public SettingsFractals1074(Settings s) {
        
        super(s);
        trueColorOut = s.fns.tcs.trueColorOut;
        trueColorIn = s.fns.tcs.trueColorIn;
        trueColorOutMode = s.fns.tcs.trueColorOutMode;
        trueColorInMode = s.fns.tcs.trueColorInMode;
        trueColorOutPreset = s.fns.tcs.trueColorOutPreset;
        trueColorInPreset = s.fns.tcs.trueColorInPreset;
        outTcComponent1 = s.fns.tcs.outTcComponent1;
        outTcComponent2 = s.fns.tcs.outTcComponent2;
        outTcComponent3 = s.fns.tcs.outTcComponent3;
        inTcComponent1 = s.fns.tcs.inTcComponent1;
        inTcComponent2 = s.fns.tcs.inTcComponent2;
        inTcComponent3 = s.fns.tcs.inTcComponent3;
        outTcColorSpace = s.fns.tcs.outTcColorSpace;
        inTcColorSpace = s.fns.tcs.inTcColorSpace;
        newton_hines_k = s.fns.newton_hines_k;
        showAtomDomains = s.pps.sts.showAtomDomains;
        reductionFunction = s.pps.sts.reductionFunction;
        useIterations = s.pps.sts.useIterations;
        useSmoothing = s.pps.sts.useSmoothing;
        stepsize_im = s.fns.mps.stepsize_im;
        lyapunovInitialValue = s.fns.lpns.lyapunovInitialValue;
        
    }
    
    @Override
    public int getVersion() {

        return 1074;

    }

    @Override
    public boolean isJulia() {

        return false;

    }
    
    public double getStepsizeIm() {
        return stepsize_im;
    }
    
    public String getLyapunovInitialValue() {
        return lyapunovInitialValue;
    }

    /**
     * @return the trueColorOut
     */
    public boolean getTrueColorOut() {
        return trueColorOut;
    }

    /**
     * @return the trueColorIn
     */
    public boolean getTrueColorIn() {
        return trueColorIn;
    }

    /**
     * @return the trueColorOutMode
     */
    public int getTrueColorOutMode() {
        return trueColorOutMode;
    }

    /**
     * @return the trueColorInMode
     */
    public int getTrueColorInMode() {
        return trueColorInMode;
    }

    /**
     * @return the trueColorOutPreset
     */
    public int getTrueColorOutPreset() {
        return trueColorOutPreset;
    }

    /**
     * @return the trueColorInPreset
     */
    public int getTrueColorInPreset() {
        return trueColorInPreset;
    }

    /**
     * @return the outTcComponent1
     */
    public String getOutTcComponent1() {
        return outTcComponent1;
    }

    /**
     * @return the outTcComponent2
     */
    public String getOutTcComponent2() {
        return outTcComponent2;
    }

    /**
     * @return the outTcComponent3
     */
    public String getOutTcComponent3() {
        return outTcComponent3;
    }

    /**
     * @return the inTcComponent1
     */
    public String getInTcComponent1() {
        return inTcComponent1;
    }

    /**
     * @return the inTcComponent2
     */
    public String getInTcComponent2() {
        return inTcComponent2;
    }

    /**
     * @return the inTcComponent3
     */
    public String getInTcComponent3() {
        return inTcComponent3;
    }

    /**
     * @return the outTcColorSpace
     */
    public int getOutTcColorSpace() {
        return outTcColorSpace;
    }

    /**
     * @return the inTcColorSpace
     */
    public int getInTcColorSpace() {
        return inTcColorSpace;
    }

    /**
     * @return the newton_hines_k
     */
    public double[] getNewtonHinesK() {
        return newton_hines_k;
    }

    /**
     * @return the showAtomDomains
     */
    public boolean getShowAtomDomains() {
        return showAtomDomains;
    }

    /**
     * @return the reductionFunction
     */
    public int getReductionFunction() {
        return reductionFunction;
    }

    /**
     * @return the useIterations
     */
    public boolean getUseIterations() {
        return useIterations;
    }

    /**
     * @return the useSmoothing
     */
    public boolean getUseSmoothing() {
        return useSmoothing;
    }
    
}
