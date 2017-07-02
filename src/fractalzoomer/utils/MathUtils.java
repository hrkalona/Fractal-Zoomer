/*
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
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
package fractalzoomer.utils;

import java.awt.geom.Point2D;

/**
 *
 * @author hrkalona2
 */
public class MathUtils {
    
    public static double angleBetween2PointsDegrees(double x1, double y1, double x2, double y2) {
        
        double dy = -y1 + y2;
        double dx = -x1 + x2;
        double angle = 0;
        
        if (dx == 0) // special case
            angle = dy >= 0? Math.PI/2: -Math.PI/2;
        else {
            angle = Math.atan(dy/ dx);
            if (dx < 0) // hemisphere correction
                angle += Math.PI;
        }
        // all between 0 and 2PI
        if (angle < 0) // between -PI/2 and 0
            angle += 2*Math.PI;
        
        return Math.toDegrees(angle);       
    }
    
    public static Point2D.Double rotatePointRelativeToPoint(double pointX, double pointY, double[] rotation_vals, double[] rotation_center) {
        double temp_xcenter = pointX - rotation_center[0];
        double temp_ycenter = pointY - rotation_center[1];

        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];
                        
        temp1 = temp1 == 0.0 ? 0.0 : temp1;
        temp2 = temp2 == 0.0 ? 0.0 : temp2;
                        
        return new Point2D.Double(temp1, temp2);
    }
    
    /// <summary>
/// Bezier Spline methods
/// </summary>
public static class BezierSpline
{
/// <summary>
	/// Get open-ended Bezier Spline Control Points.
	/// </summary>
	/// <param name="knots">Input Knot Bezier spline points.</param>
	/// <param name="firstControlPoints">Output First Control points
	/// array of knots.Length - 1 length.</param>
	/// <param name="secondControlPoints">Output Second Control points
	/// array of knots.Length - 1 length.</param>
	/// <exception cref="ArgumentNullException"><paramref name="knots"/>
	/// parameter must be not null.</exception>
	/// <exception cref="ArgumentException"><paramref name="knots"/>
	/// array must contain at least two points.</exception>
	public static Point2D.Double[][] GetCurveControlPoints(Point2D.Double[] knots)
	{
		
                if (knots == null)
			throw new NullPointerException("knots");
		int n = knots.length - 1;
		if (n < 1)
			throw new IllegalArgumentException("At least two knot points required");
                
                Point2D.Double[][] controlPoints = new Point2D.Double[2][];
                
		if (n == 1)
		{ // Special case: Bezier curve should be a straight line.
			controlPoints[0] = new Point2D.Double[1];
			// 3P1 = 2P0 + P3
                        controlPoints[0][0] = new Point2D.Double();
			controlPoints[0][0].x= (2 * knots[0].x + knots[1].x) / 3;
			controlPoints[0][0].y = (2 * knots[0].y + knots[1].y) / 3;

			controlPoints[1] = new Point2D.Double[1];
                        controlPoints[1][0] = new Point2D.Double();
			// P2 = 2P1 – P0
			controlPoints[1][0].x = 2 *
				controlPoints[0][0].x - knots[0].x;
			controlPoints[1][0].y = 2 *
				controlPoints[0][0].y - knots[0].y;
			return controlPoints;
		}

		// Calculate first Bezier control points
		// Right hand side vector
		double[] rhs = new double[n];

		// Set right hand side X values
		for (int i = 1; i < n - 1; ++i)
			rhs[i] = 4 * knots[i].x + 2 * knots[i + 1].x;
		rhs[0] = knots[0].x + 2 * knots[1].x;
		rhs[n - 1] = (8 * knots[n - 1].x + knots[n].x) / 2.0;
		// Get first control points X-values
		double[] x = GetFirstControlPoints(rhs);

		// Set right hand side Y values
		for (int i = 1; i < n - 1; ++i)
			rhs[i] = 4 * knots[i].y + 2 * knots[i + 1].y;
		rhs[0] = knots[0].y + 2 * knots[1].y;
		rhs[n - 1] = (8 * knots[n - 1].y + knots[n].y) / 2.0;
		// Get first control points Y-values
		double[] y = GetFirstControlPoints(rhs);

		// Fill output arrays.
		controlPoints[0] = new Point2D.Double[n];
		controlPoints[1] = new Point2D.Double[n];
		for (int i = 0; i < n; ++i)
		{
			// First control point
			controlPoints[0][i] = new Point2D.Double(x[i], y[i]);
			// Second control point
			if (i < n - 1)
				controlPoints[1][i] = new Point2D.Double(2 * knots
					[i + 1].x - x[i + 1], 2 *
					knots[i + 1].y - y[i + 1]);
			else
				controlPoints[1][i] = new Point2D.Double((knots
					[n].x + x[n - 1]) / 2,
					(knots[n].y + y[n - 1]) / 2);
		}
                
                return controlPoints;
	}

	/// <summary>
	/// Solves a tridiagonal system for one of coordinates (x or y)
	/// of first Bezier control points.
	/// </summary>
	/// <param name="rhs">Right hand side vector.</param>
	/// <returns>Solution vector.</returns>
	private static double[] GetFirstControlPoints(double[] rhs)
	{
		int n = rhs.length;
		double[] x = new double[n]; // Solution vector.
		double[] tmp = new double[n]; // Temp workspace.

		double b = 2.0;
		x[0] = rhs[0] / b;
		for (int i = 1; i < n; i++) // Decomposition and forward substitution.
		{
			tmp[i] = 1 / b;
			b = (i < n - 1 ? 4.0 : 3.5) - tmp[i];
			x[i] = (rhs[i] - x[i - 1]) / b;
		}
		for (int i = 1; i < n; i++)
			x[n - i - 1] -= tmp[n - i] * x[n - i]; // Backsubstitution.

		return x;
	}
        
        /*public static void main(String[] args) {
            
            Point2D.Double[] knots = new Point2D.Double[3];
            
  
            knots[0] = new Point2D.Double(0, 0);
            knots[1] = new Point2D.Double(1, 75);
            knots[2] = new Point2D.Double(2, 143);
            Point2D.Double[][] controlPoints = BezierSpline.GetCurveControlPoints(knots);
            
            /*System.out.println("P0: " +  knots[0].x + " " +  knots[0].y);
            System.out.println("P1: " +  controlPoints[0][0].x + " " +  controlPoints[0][0].y);
            System.out.println("P2: " +  controlPoints[1][0].x + " " +  controlPoints[1][0].y);
            System.out.println("P3: " +  knots[1].x + " " +  knots[1].y);
            
            for(int i = 0; i < knots.length; i++) {
                System.out.println("P" + (i) + ": " +  knots[i].x + " " +  knots[i].y / 255);
                if(i < controlPoints.length) {
                    System.out.println("C" + (i) + "0: " +  controlPoints[0][i].x + " " +  controlPoints[0][i].y / 255);
                    System.out.println("C" + (i) + "1: " +  controlPoints[1][i].x + " " +  controlPoints[1][i].y / 255);
                }
            }
        }*/
}
}
