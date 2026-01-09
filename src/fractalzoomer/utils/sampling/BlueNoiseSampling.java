package fractalzoomer.utils.sampling;

import fractalzoomer.utils.space_filling_curves.RenderFrame;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlueNoiseSampling {
    public static long seed;

    static {
       seed = 2;
    }

    public static void setSeed(long seedIn) {
        seed = seedIn;
    }

    public static List<Point2D.Double> generateBlueNoiseSamples(double width, double height, int N, int candidates) {
        List<Point2D.Double> points = new ArrayList<>();

        // First point is completely random
        //points.add(new Point(rand.nextDouble() * width, rand.nextDouble() * height));
        points.add(new Point2D.Double(width / 2, height / 2));

        Random rand = new Random(seed);

        while (points.size() < N) {
            Point2D.Double bestCandidate = null;
            double bestDistance = -1;

            for (int i = 0; i < candidates; i++) {
                Point2D.Double candidate = new Point2D.Double(rand.nextDouble() * width, rand.nextDouble() * height);
                double minDist = minDistance(candidate, points);

                if (minDist > bestDistance) {
                    bestDistance = minDist;
                    bestCandidate = candidate;
                }
            }

            points.add(bestCandidate);
        }

        return points;
    }

    private static double minDistance(Point2D.Double p, List<Point2D.Double> points) {
        double minDist = Double.MAX_VALUE;
        for (Point2D.Double other : points) {
            //double dist = Math.hypot(p.x - other.x, p.y - other.y);
            double dx = other.x - p.x;
            double dy = other.y - p.y;
            double dist = Math.sqrt(dx * dx + dy * dy);
            minDist = Math.min(minDist, dist);
        }
        return minDist;
    }

    public static void main(String[] args) {
        double width = 2.3794416243654823E-4, height = 2.3794416243654823E-4;
        int N = 81, candidates = 200;
        List<Point2D.Double> samples = generateBlueNoiseSamples(width, height, N, candidates);

        BufferedImage img = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
        for (Point2D.Double p : samples) {
            //System.out.printf("(%.3f, %.3f)\n", p.x, p.y);
            g2d.setColor(Color.BLACK);
            g2d.fillOval((int)((p.x / height) * img.getHeight()), (int)((p.y / width) * img.getWidth()), 5, 5);
        }
        g2d.dispose();

        System.out.println(samples.size());

        new RenderFrame(img);
    }
}

