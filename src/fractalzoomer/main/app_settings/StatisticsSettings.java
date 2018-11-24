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
package fractalzoomer.main.app_settings;

import fractalzoomer.main.Constants;

/**
 *
 * @author hrkalona2
 */
public class StatisticsSettings implements Constants {
    public boolean statistic;
    public int statistic_type;
    public double statistic_intensity;
    public double stripeAvgStripeDensity;
    public double cosArgStripeDensity;
    public double cosArgInvStripeDensity;
    public double StripeDenominatorFactor;
    public int statisticGroup;
    public String user_statistic_formula;
    public boolean useAverage;
    public int statistic_escape_type;
    
    public StatisticsSettings(StatisticsSettings copy) {
        statistic = copy.statistic;
        statistic_type = copy.statistic_type;
        statistic_intensity = copy.statistic_intensity;
        stripeAvgStripeDensity = copy.stripeAvgStripeDensity;
        cosArgStripeDensity = copy.cosArgStripeDensity;
        cosArgInvStripeDensity = copy.cosArgInvStripeDensity;
        StripeDenominatorFactor = copy.StripeDenominatorFactor;
        statisticGroup = copy.statisticGroup;
        user_statistic_formula = copy.user_statistic_formula;
        useAverage = copy.useAverage;
        statistic_escape_type = copy.statistic_escape_type;
    }
    
    public StatisticsSettings() {
        statistic = false;
        statistic_type = STRIPE_AVERAGE;
        statistic_intensity = 1;
        stripeAvgStripeDensity = 2.5;
        cosArgStripeDensity = 12;
        cosArgInvStripeDensity = 6;
        StripeDenominatorFactor = 12;
        statisticGroup = 0;
        user_statistic_formula = "(0.5 * cos(12 * arg(z)) + 0.5) / norm(z)";
        useAverage = true;
        statistic_escape_type = ESCAPING;
    }
}
