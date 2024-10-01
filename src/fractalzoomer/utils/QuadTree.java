package fractalzoomer.utils;
import fractalzoomer.core.TaskRender;
import fractalzoomer.core.rendering_algorithms.MarianiSilver3Render;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class QuadTree implements Serializable {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 5855898554454654068L;
	private int height;
	private int width;
	private Node root;

	private double accuracy;
	private double threshold;
	private int algorithm;

	private int threshold_calculation;

	private boolean dynamicThreshold;
	private int dynamicThresholdCurve;

	private boolean mergeNodes;


	private int max_tree_height;

	public QuadTree(int[] image, int width, int height, double threshold, int algorithm, boolean dynamicThreshold, int dynamicThresholdCurve, int threshold_calculation, boolean mergeNodes) {

		this.height = height;
		this.width = width;

		if(algorithm == 3) {
			algorithm = 4;
		}
		else if(algorithm == 4) {
			algorithm = 3;
		}

		this.threshold = threshold;
		this.algorithm = algorithm;
		this.dynamicThreshold = dynamicThreshold;
		this.dynamicThresholdCurve = dynamicThresholdCurve;
		this.threshold_calculation = threshold_calculation;
		this.mergeNodes = mergeNodes && algorithm == 0;

		max_tree_height = 0;

		compress(image);

	}

	public static int MAX_LEVEL = 33;
	public static int MIN_BLOCK_AREA = 0;

	@Deprecated
	private Node compress(int[] image, int image_width, int i, int j, int h, int w, int level) {

		Node node = new Node(j, i, h, w);

		if(level >= MAX_LEVEL) {
			node.setColor(getAreaAverage(image, image_width, i, j, h, w));
			return node;
		}

		if(w * h <= MIN_BLOCK_AREA) {
			node.setColor(getAreaAverage(image, image_width, i, j, h, w));
			return node;
		}

		int c;

		Node[] Q = node.getQ();
		int levelp1 = level + 1;

		if(h == 1 && w == 1) {
			node.setColor(image[i * image_width + j]);
		}
		else if((c = getNodeColor(image, image_width, i, j, h, w)) != 0) {
			node.setColor(c);
		}
		/*else if(h == 1 || w == 1) {
			if(h == 1) {
				if(w <= 4) {
					for(int k = 0; k < w; ++k) {
						Q[k] = compress(image, image_width, i, j + k, h, 1, levelp1);
					}
				}
				else {
					int w_ = w / 2;
					Q[0] = compress(image, image_width, i, j, 1, w_, levelp1);
					Q[1] = compress(image, image_width, i, j + w_, 1, w - w_, levelp1);
				}
			}
			else {
				if(h <= 4) {
					for(int k = 0; k < h; ++k) {
						Q[k] = compress(image, image_width, i + k, j, 1, w, levelp1);
					}
				}
				else {
					int h_ = h / 2;
					Q[0] = compress(image, image_width, i, j, h_, 1, levelp1);
					Q[1] = compress(image, image_width, i + h_, j, h - h_, 1, levelp1);
				}
			}
			//define a cor do nó de acordo com a méida das cores dos filhos
			node.setColor(node.averageChildren());
		}*/
		else {
			if(algorithm == 1 || algorithm == 2 || algorithm == 3 || algorithm == 4) { //Binary
				if(MarianiSilver3Render.getSplitMode(algorithm, level) == 1) {
					int h_half = h >>> 1;
					int ih = i + h_half;
					int hdiff = h - h_half;

					if (h_half > 0) {
						Q[Node.QuadName.TOP_LEFT.ordinal()] = compress(image, image_width, i, j, h_half, w, levelp1);
					}

					if (hdiff > 0) {
						Q[Node.QuadName.BOTTOM_LEFT.ordinal()] = compress(image, image_width, ih, j, hdiff, w, levelp1);
					}
				}
				else {
					int w_half = w >>> 1;
					int jw = j + w_half;
					int wdiff = w - w_half;

					if (w_half > 0) {
						Q[Node.QuadName.TOP_LEFT.ordinal()] = compress(image, image_width, i, j, h, w_half, levelp1);
					}

					if (wdiff > 0) {
						Q[Node.QuadName.TOP_RIGHT.ordinal()] = compress(image, image_width, i, jw, h, wdiff, levelp1);
					}
				}
			}
			else { //Quad
				int h_half = h >>> 1;
				int w_half = w >>> 1;
				int wdiff = w - w_half;
				int hdiff = h - h_half;
				int jw = j + w_half;
				int ih = i + h_half;

				if (h_half > 0 && w_half > 0) {
					Q[Node.QuadName.TOP_LEFT.ordinal()] = compress(image, image_width, i, j, h_half, w_half, levelp1);
				}

				if (h_half > 0 && wdiff > 0) {
					Q[Node.QuadName.TOP_RIGHT.ordinal()] = compress(image, image_width, i, jw, h_half, wdiff, levelp1);
				}

				if (hdiff > 0 && w_half > 0) {
					Q[Node.QuadName.BOTTOM_LEFT.ordinal()] = compress(image, image_width, ih, j, hdiff, w_half, levelp1);
				}

				if (hdiff > 0 && wdiff > 0) {
					Q[Node.QuadName.BOTTOM_RIGHT.ordinal()] = compress(image, image_width, ih, jw, hdiff, wdiff, levelp1);
				}
			}

			node.setColor(node.averageChildren());
		}

		return node;

	}

	class MergedNodes {
		ArrayList<Node> nodes;

		public MergedNodes(ArrayList<Node> nodes) {
			this.nodes = nodes;
		}

		public boolean isLeaf() {
			if(nodes.isEmpty()) {
				return false;
			}
			return nodes.get(0).isLeaf();
		}

		public int getColor() {
			if(nodes.isEmpty()) {
				return 0;
			}
			return nodes.get(0).getColor();
		}
	}

	class TraversalData {
		MergedNodes nodes;
		Node current;
		int level;
		public TraversalData(Node current, int level) {
			this.current = current;
			this.level = level;
		}

		public TraversalData(MergedNodes nodes, int level) {
			this.nodes = nodes;
			this.level = level;
		}
	}

	private void compress(int[] image) {

		root = new Node(0, 0, height, width);
		Stack<TraversalData> stack = new Stack<>();
		stack.add(new TraversalData(root,  0));

		Stack<Node> post_order_stack = new Stack<>();

		int i, j, h, w;

		while (!stack.isEmpty()) {
			TraversalData data = stack.pop();

			Node node = data.current;

			i = node.getY();
			j = node.getX();
			h = node.getHeight();
			w = node.getWidth();

			if(data.level > max_tree_height) {
				max_tree_height = data.level;
			}

			if(data.level >= MAX_LEVEL) {
				node.setColor(getAreaAverage(image, width, i, j, h, w));
				continue;
			}

			if(node.getArea() <= MIN_BLOCK_AREA) {
				node.setColor(getAreaAverage(image, width, i, j, h, w));
				continue;
			}

			int c;
			if(h == 1 && w == 1) {
				node.setColor(image[i * width + j]);
				continue;
			}

			if((c = getNodeColor(image, width, i, j, h, w)) != 0) {
				node.setColor(c);
				continue;
			}

			post_order_stack.add(node);

			int levelp1 = data.level + 1;
			Node[] Q = node.getQ();

			if(algorithm == 1 || algorithm == 2 || algorithm == 3 || algorithm == 4) { //Binary
				if(MarianiSilver3Render.getSplitMode(algorithm, data.level) == 1) {
					int h_half = h >>> 1;
					int ih = i + h_half;
					int hdiff = h - h_half;

					if (h_half > 0) {
						Node newNode = new Node(j, i, h_half, w);
						Q[Node.QuadName.TOP_LEFT.ordinal()] = newNode;
						stack.add(new TraversalData(newNode, levelp1));
					}

					if (hdiff > 0) {
						Node newNode = new Node(j, ih, hdiff, w);
						Q[Node.QuadName.BOTTOM_LEFT.ordinal()] = newNode;
						stack.add(new TraversalData(newNode, levelp1));
					}
				}
				else {
					int w_half = w >>> 1;
					int jw = j + w_half;
					int wdiff = w - w_half;

					if (w_half > 0) {
						Node newNode = new Node(j, i, h, w_half);
						Q[Node.QuadName.TOP_LEFT.ordinal()] = newNode;
						stack.add(new TraversalData(newNode, levelp1));
					}

					if (wdiff > 0) {
						Node newNode = new Node(jw, i, h, wdiff);
						Q[Node.QuadName.TOP_RIGHT.ordinal()] = newNode;
						stack.add(new TraversalData(newNode, levelp1));
					}
				}
			}
			else { //Quad
				int h_half = h >>> 1;
				int w_half = w >>> 1;
				int wdiff = w - w_half;
				int hdiff = h - h_half;
				int jw = j + w_half;
				int ih = i + h_half;

				if (h_half > 0 && w_half > 0) {
					Node newNode = new Node(j, i, h_half, w_half);
					Q[Node.QuadName.TOP_LEFT.ordinal()] = newNode;
					stack.add(new TraversalData(newNode, levelp1));
				}

				if (h_half > 0 && wdiff > 0) {
					Node newNode = new Node(jw, i, h_half, wdiff);
					Q[Node.QuadName.TOP_RIGHT.ordinal()] = newNode;
					stack.add(new TraversalData(newNode, levelp1));
				}

				if (hdiff > 0 && w_half > 0) {
					Node newNode = new Node(j, ih, hdiff, w_half);
					Q[Node.QuadName.BOTTOM_LEFT.ordinal()] = newNode;
					stack.add(new TraversalData(newNode, levelp1));
				}

				if (hdiff > 0 && wdiff > 0) {
					Node newNode = new Node(jw, ih, hdiff, wdiff);
					Q[Node.QuadName.BOTTOM_RIGHT.ordinal()] = newNode;
					stack.add(new TraversalData(newNode, levelp1));
				}
			}
		}

		while (!post_order_stack.isEmpty()) {
			Node node = post_order_stack.pop();
			node.setColor(node.averageChildren());
		}
	}

	@Deprecated
	private int histogramPercent(int[] image, int image_width, int i, int j, int h, int w) {
		Map<Integer, Integer> colors = new HashMap<>();

		double r = 0.0;
		double g = 0.0;
		double b = 0.0;

		for(int k = 0; k < h; ++k) {

			for(int l = 0; l < w; ++l) {

				int c = image[(k + i) * image_width + l + j];

				r += (c >> 16) & 0xff;
				g += (c >> 8) & 0xff;
				b += (c) & 0xff;

				if(!colors.containsKey(c)) {
					colors.put(c, 1);
				}
				else {
					int count = colors.get(c) + 1;
					colors.put(c, count);
				}

			}

		}

		int size = h * w;

		r /= size;
		g /= size;
		b /= size;

		int max = 0;
		for (Integer c : colors.keySet()) {

			int count = colors.get(c);
			max = Math.max(max, count);

		}

		double currentAccuracy = (double) max / size;

		return (currentAccuracy >= accuracy) ? 0xff000000 | (((int)(r + 0.5)) << 16) | (((int)(g + 0.5)) << 8) | (((int)(b + 0.5))) : 0;

	}

	private int manhattanDistanceThreshold(int[] image, int image_width, int i, int j, int h, int w) {
		int averageColor = getAreaAverage(image, image_width, i, j, h, w);
		int red = (averageColor >> 16) & 0xff;
		int green = (averageColor >> 8) & 0xff;
		int blue = (averageColor) & 0xff;
		int colorSumRed = 0;
		int colorSumGreen = 0;
		int colorSumBlue = 0;

		int area = w * h;

		// Calculates the distance between every pixel in the region
		// and the average color. The Manhattan distance is used, and
		// all the distances are added.
		for(int k = 0; k < h; ++k) {

			for(int l = 0; l < w; ++l) {

				int c = image[(k + i) * image_width + l + j];

				int r = (c >> 16) & 0xff;
				int g = (c >> 8) & 0xff;
				int b = (c) & 0xff;

				colorSumRed += Math.abs(red - r);
				colorSumGreen += Math.abs(green - g);
				colorSumBlue += Math.abs(blue - b);
			}

		}

		colorSumRed /= area;
		colorSumGreen /= area;
		colorSumBlue /= area;

		double avg = averageColorValues(colorSumRed, colorSumGreen, colorSumBlue);

		// Calculates the average distance, and returns the result.
		// We divide by three, because we are averaging over 3 channels.
		if(avg <= getThreshold(threshold, h, w)) {
			return 0xff000000 | (((int)(red + 0.5)) << 16) | (((int)(green + 0.5)) << 8) | (((int)(blue + 0.5)));
		}

		return 0;
	}
	private double averageColorValues(double red, double green, double blue) {
		if(threshold_calculation == 0) {
			return (red + green + blue) / 3.0;
		}
		else if(threshold_calculation == 1) { //NTSC
			return red * 0.299 + green * 0.587 + blue * 0.114;
		}
		else if(threshold_calculation == 1) { //HDTV
			return red * 0.2126 + green * 0.7152 + blue * 0.0722;
		}

		return red * 0.2627 + green * 0.6780 + blue * 0.0593;//HDR
	}

	private int stdThreshold(int[] image, int image_width, int i, int j, int h, int w) {
		double meanR = 0;
		double meanG = 0;
		double meanB = 0;
		double varR = 0;
		double varG = 0;
		double varB = 0;
		int count = 0;

		for(int k = 0; k < h; ++k) {

			for(int l = 0; l < w; ++l) {

				int c = image[(k + i) * image_width + l + j];

				double r = (c >> 16) & 0xff;
				double g = (c >> 8) & 0xff;
				double b = (c) & 0xff;

				count++;
				double countReciprocal = 1.0 / count;

				double deltaR = r - meanR;
				double deltaG = g - meanG;
				double deltaB = b - meanB;

				meanR += deltaR * countReciprocal;
				meanG += deltaG * countReciprocal;
				meanB += deltaB * countReciprocal;


				varR += deltaR * (r - meanR);
				varG += deltaG * (g - meanG);
				varB += deltaB * (b - meanB);
			}

		}

		varR /= count;
		varG /= count;
		varB /= count;

		// Calculate total std
		double totalStd = averageColorValues(Math.sqrt(varR), Math.sqrt(varG), Math.sqrt(varB));

		if(totalStd <= getThreshold(threshold, h, w)) {
			return 0xff000000 | (((int)(meanR + 0.5)) << 16) | (((int)(meanG + 0.5)) << 8) | (((int)(meanB + 0.5)));
		}

		return 0;
	}
	private int getNodeColor(int[] image, int image_width, int i, int j, int h, int w) {
		return stdThreshold(image, image_width, i, j, h, w);
	}

	private int getAreaAverage(int[] image, int image_width, int i, int j, int h, int w) {

		double r = 0.0;
		double g = 0.0;
		double b = 0.0;


		for(int k = 0; k < h; ++k) {

			for(int l = 0; l < w; ++l) {

				int c = image[(k + i) * image_width + l + j];

				r += (c >> 16) & 0xff;
				g += (c >> 8) & 0xff;
				b += (c) & 0xff;
			}

		}

		int size = h * w;

		if(size > 0) {
			r /= size;
			g /= size;
			b /= size;
		}

		return 0xff000000 | (((int)(r + 0.5)) << 16) | (((int)(g + 0.5)) << 8) | (((int)(b + 0.5)));

	}

	@Deprecated
	public int treeHeight() {
		return treeHeight(root);
	}

	public int maxTreeHeight() {
		return max_tree_height + 1;
	}

	@Deprecated
	private int treeHeight(Node node) {

		if(node == null) return 0;

		int h1 = treeHeight(node.getQ()[0]);
		int h2 = treeHeight(node.getQ()[1]);
		int h3 = treeHeight(node.getQ()[2]);
		int h4 = treeHeight(node.getQ()[3]);

		int max = (h1 > h2) ? h1 : (h2 > h3) ? h2 : Math.max(h3, h4);

		return max + 1;

	}

	public Node getRoot() {
		return root;
	}

	public int getNumberOfQuads(Node node, int level) {
		if(level == 0 || node.isLeaf()) {
			return 1;
		}

		int sum = 0;
		Node[] Q = node.getQ();
		for(int i = 0; i < Q.length; ++i) {
			if(Q[i] != null) {
				sum += getNumberOfQuads(Q[i], level - 1);
			}
		}
		return sum;
	}

	public double getCompressionRatio(int level) {
		return ((double)getNumberOfQuads(root, level)) / (width * height);
	}

	public double getCompressionRatio() {
		return ((double)getNumberOfQuads(root, Integer.MAX_VALUE)) / (width * height);
	}
	private double getThreshold(double threshold, int h, int w) {
		if(!dynamicThreshold) {
			return threshold;
		}
		double coef = ((double)(w * h)) / (width * height);
		return threshold * TaskRender.fade(dynamicThresholdCurve,1 - coef);
	}

	private int colorize(int level, int fillAlgorithm, int fillColor, int originalColor) {
		switch (fillAlgorithm) {
			case 0:
				return originalColor;
			case 1:
				return TaskRender.algorithm_colors[(level + TaskRender.randomNumber) % TaskRender.algorithm_colors.length];
			case 2:
				return fillColor;
		}
		return 0;
	}

	public void paintQuadImage(int[] image, int image_width, int image_height, int start_level, boolean showDivision, int fillAlgorithm, int divisionColor, int fillColor) {
		Stack<TraversalData> stack = new Stack<>();
		stack.add(new TraversalData(root, start_level));

		while (!stack.isEmpty()) {
			TraversalData data = stack.pop();
			Node node = data.current;
			MergedNodes mergedNodes = data.nodes;

			if(mergedNodes != null && (data.level == 0 || mergedNodes.isLeaf())) {

				ArrayList<Node> nodes = mergedNodes.nodes;
				for(Node n : nodes) {
					int x = n.getX();
					int y = n.getY();

					boolean hasAbove = false, hasLeft = false;

					for(Node n2 : nodes) {
						if(n2 == n) {
							continue;
						}
						if(x == n2.getX() && y > n2.getY()) {
							hasAbove = true;
						}
						if(y == n2.getY() && x > n2.getX()) {
							hasLeft = true;
						}
					}

					paintLeaf(image, image_width, image_height, n, data.level, fillAlgorithm, fillColor, showDivision, divisionColor, hasLeft, hasAbove);
				}
				continue;
			}
			else if(node != null && (data.level == 0 || node.isLeaf())) {
				paintLeaf(image, image_width, image_height, node, data.level, fillAlgorithm, fillColor, showDivision, divisionColor, false, false);
				continue;
			}

			int levelm1 = data.level - 1;

			if(mergeNodes) {
				ArrayList<Node> ns;
				if(node != null) {
					ns = new ArrayList<>();
					ns.add(node);
				}
				else {
					ns = mergedNodes.nodes;
				}

				for(Node n : ns) {
					Node[] Q = n.getQ();
					int[] colors = new int[Q.length];
					ArrayList<Node>[] lists = new ArrayList[colors.length];
					for(int i = 0; i < lists.length; i++) {
						lists[i] = new ArrayList<>();
					}

					int colors_added = 0;
					for (int i = 0; i < Q.length; ++i) {
						if (Q[i] != null && (Q[i].isLeaf() || levelm1 == 0)) {
							int color = Q[i].getColor();

							int index = 0;
							for(; index < colors_added && index < colors.length; index++) {
								if(colors[index] == color){
									break;
								}
							}

							if(index == colors_added) {
								colors[colors_added] = color;
								colors_added++;
							}

							lists[index].add(Q[i]);
						}
						else if(Q[i] != null) {
							stack.add(new TraversalData(Q[i], levelm1));
						}
					}

					for (int j = 0; j < colors_added && j < colors.length; j++) {
						ArrayList<Node> list = lists[j];
						if(list.size() > 1) {
							stack.add(new TraversalData(new MergedNodes(list), levelm1));
						}
						else {
							stack.add(new TraversalData(list.get(0), levelm1));
						}
					}
				}
			}
			else {
				Node[] Q = node.getQ();
				for(int i = 0; i < Q.length; ++i) {
					if(Q[i] != null) {
						stack.add(new TraversalData(Q[i], levelm1));
					}
				}
			}
		}
	}

	private void paintLeaf(int[] image, int image_width, int image_height, Node node, int level, int fillAlgorithm, int fillColor, boolean showDivision, int divisionColor, boolean hasLeft, boolean hasAbove) {
		int color = colorize(level, fillAlgorithm, fillColor, node.getColor());

		int x = node.getX();
		int y = node.getY();

		int w = node.getWidth();
		int h = node.getHeight();

		int endy = y + h;
		int endx = x + w;
		for(int cury = y; cury < endy; cury++) {
			for(int curx = x; curx < endx; curx++) {
				if(showDivision) {
					if (	(curx == x && !hasLeft)
							|| (cury == y && !hasAbove)
							|| curx == image_width - 1 || cury == image_height - 1
							|| (hasLeft && hasAbove && curx == x && cury == y)) {
						image[cury * image_width + curx] = divisionColor;
					} else {
						image[cury * image_width + curx] = color;
					}
				}
				else {
					image[cury * image_width + curx] = color;
				}
			}
		}
	}

	@Deprecated
	public void paintQuadImage(int[] image, int image_width, int image_height, Node node, int level, boolean showDivision, int fillAlgorithm, int divisionColor, int fillColor) {

		//mergeNodes is not implemented here
		if(level == 0 || node.isLeaf()) {
			paintLeaf(image, image_width, image_height, node, level, fillAlgorithm, fillColor, showDivision, divisionColor, false, false);
			return;
		}

		Node[] Q = node.getQ();
		int levelm1 = level - 1;
		for(int i = 0; i < Q.length; ++i) {
			if(Q[i] != null) {
				paintQuadImage(image, image_width, image_height, Q[i], levelm1, showDivision, fillAlgorithm, divisionColor, fillColor);
			}
		}

	}
}
