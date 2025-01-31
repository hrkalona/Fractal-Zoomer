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

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.util.stream.IntStream;

/**
 * An abstract superclass for point filters. The interface is the same as the old RGBImageFilter.
 */
public abstract class PointFilter extends AbstractBufferedImageOp {

        @Override
    public BufferedImage filter( BufferedImage src, BufferedImage dst ) {
        int width = src.getWidth();
        int height = src.getHeight();
		int type = src.getType();
		WritableRaster srcRaster = src.getRaster();

        if ( dst == null )
            dst = createCompatibleDestImage( src, null );
		WritableRaster dstRaster = dst.getRaster();

        setDimensions( width, height);

		if(srcRaster.getDataBuffer() instanceof DataBufferInt && dstRaster.getDataBuffer() instanceof  DataBufferInt) {

			int[] srcrgbs = ((DataBufferInt) srcRaster.getDataBuffer()).getData();
			int[] dstrgbs = ((DataBufferInt) dstRaster.getDataBuffer()).getData();
			IntStream.range(0, srcrgbs.length)
					.parallel().forEach(p -> {
						int y = p / width;
						int x = p % width;

						dstrgbs[p] = filterRGB(y, x, srcrgbs[p]);
					});
		}
		else {
			int[] inPixels = new int[width];

			for ( int y = 0; y < height; y++ ) {
				// We try to avoid calling getRGB on images as it causes them to become unmanaged, causing horrible performance problems.
				if ( type == BufferedImage.TYPE_INT_ARGB ) {
					srcRaster.getDataElements( 0, y, width, 1, inPixels );

					final int finalY = y;
					IntStream.range(0, width)
							.parallel().forEach(x -> {
								//for ( int x = 0; x < width; x++ )
								inPixels[x] = filterRGB(x, finalY, inPixels[x]);
							});
					dstRaster.setDataElements( 0, y, width, 1, inPixels );
				} else {
					src.getRGB( 0, y, width, 1, inPixels, 0, width );
					final int finalY = y;
					IntStream.range(0, width)
							.parallel().forEach(x -> {
								//for ( int x = 0; x < width; x++ )
								inPixels[x] = filterRGB(x, finalY, inPixels[x]);
							});
					dst.setRGB( 0, y, width, 1, inPixels, 0, width );
				}
			}
		}


        return dst;
    }

	public void setDimensions(int width, int height) {
	}

	public abstract int filterRGB(int x, int y, int rgb);
}
