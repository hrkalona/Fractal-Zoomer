package fractalzoomer.utils.sampling;

import fractalzoomer.utils.space_filling_curves.RenderFrame;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPointSampling {

    public static List<Point2D.Double> generateRandomSamples(double width, double height, int N) {

        List<Point2D.Double> points = new ArrayList<>();
        double centerX = width / 2;
        double centerY = height / 2;
        double minDistSquared = 1e-12; // Squared threshold to avoid sqrt calculation
        points.add(new Point2D.Double(centerX, centerY));

        Random rand = new Random(BlueNoiseSampling.seed);

        while (points.size() < N) {
            double x = rand.nextDouble() * width;
            double y = rand.nextDouble() * height;

            double dx = x - centerX;
            double dy = y - centerY;
            double distSquared = dx * dx + dy * dy;

            // Exclude points too close to the center using squared distance check
            if (distSquared > minDistSquared) {
                points.add(new Point2D.Double(x, y));
            }
        }
        return points;
    }

    public static void main(String[] args) {
        double width = 0.007614213197969543, height = 0.007614213197969543;
        int N = 49;
        List<Point2D.Double> samples = generateRandomSamples(width, height, N);

        BufferedImage img = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
        for (int i = 0; i < samples.size(); i++) {
            Point2D.Double p = samples.get(i);
            g2d.setColor(Color.BLACK);
            g2d.fillOval((int)((p.x / height) * img.getHeight()), (int)((p.y / width) * img.getWidth()), 5, 5);
        }

        double[] aaJitterKernelX;
        double[] aaJitterKernelY;

        aaJitterKernelX = new double[Math.min(samples.size(), N)];
        aaJitterKernelY = new double[Math.min(samples.size(), N)];

        Random r  = new Random(0);

        double AA_JITTER_SIZE = 0.25;
        for (int i = 0; i < aaJitterKernelX.length; i++) {
            double jitter_size = AA_JITTER_SIZE;
            aaJitterKernelX[i] = (r.nextDouble() - 0.5) * 2 * jitter_size;
            aaJitterKernelY[i] = (r.nextDouble() - 0.5) * 2 * jitter_size;
        }
        List<Point2D.Double> jitteredSamples = new ArrayList<>();
        for (int i = 0; i < aaJitterKernelX.length; i++) {
            jitteredSamples.add(new Point2D.Double(samples.get(i).x + aaJitterKernelX[i] * (height / 2 / Math.sqrt(N)),
                    samples.get(i).y + aaJitterKernelY[i] * (width / 2 / Math.sqrt(N))));
        }

        for (Point2D.Double p : jitteredSamples) {
            g2d.setColor(Color.RED);
            g2d.fillOval((int)((p.x / height) * img.getHeight()), (int)((p.y / width) * img.getWidth()), 5, 5);
        }

        g2d.dispose();

        System.out.println(samples.size());

        new RenderFrame(img);
    }
}

