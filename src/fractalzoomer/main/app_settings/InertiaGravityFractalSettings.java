/*
 * Copyright (C) 2019 hrkalona2
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
package fractalzoomer.main.app_settings;

import fractalzoomer.main.Constants;

/**
 *
 * @author hrkalona2
 */
public class InertiaGravityFractalSettings {
    public double[][] bodyLocation;
    public double[][] bodyGravity;
    public double[] inertia_contribution;
    public double[] initial_inertia;
    public double inertia_exponent;
    public int pull_scaling_function;
    public double[] time_step;
    
    public InertiaGravityFractalSettings() {
        
        bodyLocation = new double[10][2];
        bodyLocation[0][0] = 0.6;
        bodyLocation[0][1] = -0.5;
        bodyLocation[1][0] = -0.6;
        bodyLocation[1][1] = -0.5;
        bodyLocation[2][0] = 0;
        bodyLocation[2][1] = 0.5;

        bodyGravity = new double[10][2];
        bodyGravity[0][0] = 0.8;
        bodyGravity[1][0] = 0.8;
        bodyGravity[2][0] = 0.8;
        
        inertia_contribution = new double[2];
        inertia_contribution[0] = -0.4;
        inertia_contribution[1] = 0;
        
        initial_inertia = new double[2];
        initial_inertia[0] = 0;
        initial_inertia[1] = 0;
        
        time_step = new double[2];
        time_step[0] = 1;
        time_step[1] = 0;
        
        inertia_exponent = 0.7;
        
        pull_scaling_function = Constants.PULL_EXP;
        
    }
    
}
