/*
Copyright 2006 Jerry Huxtable

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package fractalzoomer.filters_utils.image;

import fractalzoomer.utils.ColorSpaceConverter;

import java.awt.image.BufferedImage;

/**
 * A filter to posterize an image.
 */
public class PosterizeFilter extends PointFilter {

	private int numLevels;
	private int[] levels;
    private boolean initialized = false;
	private int mode;

	public PosterizeFilter() {
		mode = 0;
		setNumLevels(6);
	}
	
	/**
     * Set the number of levels in the output image.
     * @param numLevels the number of levels
     * @see #getNumLevels
     */
    public void setNumLevels(int numLevels) {
		this.numLevels = numLevels;
		initialized = false;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
     * Get the number of levels in the output image.
     * @return the number of levels
     * @see #setNumLevels
     */
	public int getNumLevels() {
		return numLevels;
	}

	/**
     * Initialize the filter.
     */
    protected void initialize() {
		initialized = true;

		if(mode == 0) {
			levels = new int[256];
			if (numLevels != 1)
				for (int i = 0; i < 256; i++)
					levels[i] = 255 * (numLevels * i / 256) / (numLevels - 1);
		}

	}

	protected double posterize(double v) {
		return Math.round(v * numLevels) / (double)numLevels;
	}

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dst ) {
		if (!initialized) {
			initialize();
		}
		return super.filter( src, dst );
	}
	
        @Override
	public int filterRGB(int x, int y, int rgb) {
		int a = rgb & 0xff000000;
		int r = (rgb >> 16) & 0xff;
		int g = (rgb >> 8) & 0xff;
		int b = rgb & 0xff;

		if(mode == 0) {
			r = levels[r];
			g = levels[g];
			b = levels[b];
		}
		else if(mode == 1) {
			double[] hsb = ColorSpaceConverter.RGBtoHSB(r, g, b);

			hsb[2] = posterize(hsb[2]);

			int[] rgbs = ColorSpaceConverter.HSBtoRGB(hsb);

			r = rgbs[0];
			g = rgbs[1];
			b = rgbs[2];
		}
		else if(mode == 2) {
			double[] hsl = ColorSpaceConverter.RGBtoHSL(r, g, b);

			hsl[2] = posterize(hsl[2]);

			int[] rgbs = ColorSpaceConverter.HSLtoRGB(hsl);

			r = rgbs[0];
			g = rgbs[1];
			b = rgbs[2];
		}
		else if(mode == 3) {
			double[] lab = ColorSpaceConverter.RGBtoLAB(r, g, b);

			lab[0] = 100.0 * posterize(lab[0] / 100.0);

			int[] rgbs = ColorSpaceConverter.LABtoRGB(lab);

			r = rgbs[0];
			g = rgbs[1];
			b = rgbs[2];
		}

		return a | (r << 16) | (g << 8) | b;
	}

        @Override
	public String toString() {
		return "Colors/Posterize...";
	}

}

