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

import fractalzoomer.core.ImageFilters;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.util.stream.IntStream;

/**
 *
 */
public class BilateralFilter extends PointFilter {
	private int kernel_size;
	private int kernel_size2;
	private double sigmaR;
	private double sigmaS;
	private double sigma_s_reciprocal;
	private double[] gaussian_kernel;

	public BilateralFilter(int kernel_size, double sigmaR, double sigmaS) {
		if(sigmaR == 0) {
			sigmaR = 0.3* ((kernel_size - 1) * 0.5 - 1) + 0.8;
		}

		if(sigmaS == 0) {
			sigmaS = 0.3* ((kernel_size - 1) * 0.5 - 1) + 0.8;
		}

		this.sigmaR = sigmaR;
		this.sigmaS = sigmaS;
		sigma_s_reciprocal = 1 / sigmaS;
		this.kernel_size = kernel_size;
	    kernel_size2 = kernel_size / 2;

		gaussian_kernel = ImageFilters.createGaussianKernel(kernel_size, sigmaR);
	}

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dst ) {
		int width = src.getWidth();
		int height = src.getHeight();
		WritableRaster srcRaster = src.getRaster();

		if ( dst == null )
			dst = createCompatibleDestImage( src, null );
		WritableRaster dstRaster = dst.getRaster();

		setDimensions( width, height);

		int[] srcrgbs = ((DataBufferInt) srcRaster.getDataBuffer()).getData();
		int[] dstrgbs = ((DataBufferInt) dstRaster.getDataBuffer()).getData();
		IntStream.range(0, srcrgbs.length)
				.parallel().forEach(p -> {
					int y = p / width;
					int x = p % width;
					dstrgbs[p] = apply(y, x, p, srcrgbs, width);
				});


		return dst;
	}


	@Override
	public int filterRGB(int x, int y, int rgb) {
		return 0;
	}

	public int apply(int y, int x, int index, int[] input, int image_size) {
		double sumRed = 0;
		double sumGreen = 0;
		double sumBlue = 0;
		int color = input[index];
		int centerRed = (color >> 16) & 0xff;
		int centerGreen = (color >> 8) & 0xff;
		int centerBlue = (color) & 0xff;
		double combined_coef_sum_red = 0;
		double combined_coef_sum_green = 0;
		double combined_coef_sum_blue = 0;

		for (int k = y - kernel_size2, p = 0; p < kernel_size; k++, p++) {
			for (int l = x - kernel_size2, t = 0; t < kernel_size; l++, t++) {

				if(k < 0 || k >= image_size || l < 0 || l >= image_size) {
					continue;
				}

				int currentVal = input[k * image_size + l];
				int Red = (currentVal >> 16) & 0xff;
				int Green = (currentVal >> 8) & 0xff;
				int Blue = (currentVal) & 0xff;

				double coef = gaussian_kernel[p * kernel_size + t];
				double combined_coef_red =  coef * similarity(Red, centerRed);
				double combined_coef_green = coef * similarity(Green, centerGreen);
				double combined_coef_blue = coef * similarity(Blue, centerBlue);
				sumRed += Red * combined_coef_red;
				sumGreen += Green * combined_coef_green;
				sumBlue += Blue * combined_coef_blue;
				combined_coef_sum_red += combined_coef_red;
				combined_coef_sum_green += combined_coef_green;
				combined_coef_sum_blue += combined_coef_blue;
			}
		}

		return 0xff000000 |  ((int)(sumRed / combined_coef_sum_red + 0.5) << 16) | ((int)(sumGreen / combined_coef_sum_green + 0.5) << 8) | ((int)(sumBlue / combined_coef_sum_blue + 0.5));
	}

	private double similarity(double val, double centralVal) {
		double distance = Math.abs(val - centralVal);
		double exponent = distance * sigma_s_reciprocal;
		exponent = exponent * exponent;
		return Math.exp(-0.5 * exponent);
	}

        @Override
	public String toString() {
		return "Bilateral";
	}

}
