package fractalzoomer.utils;

import java.io.Serializable;

public class Node implements Serializable {
	public static int MAX_CHILDREN = 4;

	/**
	 * Enumeration for representing the location of a quadrant.
	 */
	enum QuadName {
		TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
	}
	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = -3699702034980747558L;
	

	private int x;
	private int y;
	private int height;
	private int width;
	private int color;
	private Node[] q;

	public Node(int x, int y, int height, int width) {
		
		this.x = x;
		this.y = y;
		
		this.height = height;
		this.width = width;
		
		q = new Node[MAX_CHILDREN];
	}

	public int averageChildren() {
		
		double r = 0.0;
		double g = 0.0;
		double b = 0.0;
		
		int div = 0;
		
		for(int i = 0; i < q.length; ++i) {
			if(q[i] != null) {
				int color = q[i].getColor();
								
				r += (color >> 16) & 0xff;
				g += (color >> 8) & 0xff;
				b += (color) & 0xff;
				
				++div;
			}
		}

		if(div > 0) {
			r /= div;
			g /= div;
			b /= div;
		}
			
		return 0xff000000 | (((int)(r + 0.5)) << 16) | (((int)(g + 0.5)) << 8) | (((int)(b + 0.5)));

	}

	public boolean isLeaf() {
		return q[0] == null && q[1] == null && q[2] == null && q[3] == null;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Node[] getQ() {
		return q;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getArea() { return width * height; }

	@Override
	public boolean equals(Object b) {
		if(b == null) {
			return false;
		}

		if(!(b instanceof Node)) {
			return false;
		}

		Node ba = (Node) b;

		return x == ba.x && y == ba.y && width == ba.width && height == ba.height && color == ba.color;
	}

	public Node getQuadrant(QuadName name) {
		return q[name.ordinal()];
	}
}
