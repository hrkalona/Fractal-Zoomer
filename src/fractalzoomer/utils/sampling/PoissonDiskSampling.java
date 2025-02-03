package fractalzoomer.utils.sampling;

import fractalzoomer.utils.space_filling_curves.RenderFrame;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PoissonDiskSampling {

    private static boolean isValidPoint(Point2D.Double[][] grid, double cellsize, double width, double height, Point2D.Double p, double radius) {
        /* Make sure the point is on the screen */
        if (p.x < 0 || p.x >= width || p.y < 0 || p.y >= height)
            return false;

        int gwidth = grid[0].length - 1;
        int gheight = grid.length - 1;
        /* Check neighboring eight cells */
        int xindex = (int)Math.floor(p.x / cellsize);
        int yindex = (int)Math.floor(p.y / cellsize);
        int i0 = Math.max(xindex - 3, 0);
        int i1 = Math.min(xindex + 3, gwidth);
        int j0 = Math.max(yindex - 3, 0);
        int j1 = Math.min(yindex + 3, gheight);

        double radiusSqr = radius * radius;
        for (int i = i0; i <= i1; i++) {
            for (int j = j0; j <= j1; j++) {
                if (grid[i][j] != null) {
                    double dx = grid[i][j].x - p.x;
                    double dy = grid[i][j].y - p.y;
                    if (dx * dx + dy * dy < radiusSqr) {
                        return false;
                    }
                }
            }
        }

        /* If we get here, return true */
        return true;
    }

    private static void insertPoint(Point2D.Double[][] grid, double cellsize, Point2D.Double point) {
        int xindex = (int)Math.floor(point.x / cellsize);
        int yindex = (int)Math.floor(point.y / cellsize);
        grid[xindex][yindex] = point;
    }

    public static ArrayList<Point2D.Double> generatePoissonDiskSamples(double width, double height, int samples, int k) {
        double radius = Math.min(width, height) / Math.sqrt(1.25 * samples);
        /* The final set of points to return */
        ArrayList<Point2D.Double> points = new ArrayList<>();
        /* The currently "active" set of points */
        ArrayList<Point2D.Double> active = new ArrayList<>();
        /* Initial point p0 */
        Point2D.Double p0 = new Point2D.Double(width / 2, height / 2);
        Point2D.Double[][] grid;
        double cellsize = radius / Math.sqrt(2);

        Random rand = new Random(BlueNoiseSampling.seed);

        /* Figure out no. of cells in the grid for our canvas */
        int ncells_width = (int)Math.ceil(width / cellsize) + 1;
        int ncells_height = (int)Math.ceil(width / cellsize) + 1;

        /* Allocate the grid an initialize all elements to null */
        grid = new Point2D.Double[ncells_width][ncells_height];

        insertPoint(grid, cellsize, p0);
        points.add(p0);
        active.add(p0);

        while (!active.isEmpty()) {
            int random_index = rand.nextInt(active.size());
            Point2D.Double p = active.get(random_index);

            boolean found = false;
            for (int tries = 0; tries < k; tries++) {
                double theta = rand.nextDouble() * 360;
                double new_radius = rand.nextDouble() * radius + radius;
                double pnewx = p.x + new_radius * Math.cos(Math.toRadians(theta));
                double pnewy = p.y + new_radius * Math.sin(Math.toRadians(theta));
                Point2D.Double pnew = new Point2D.Double(pnewx, pnewy);

                if (!isValidPoint(grid, cellsize, width, height, pnew, radius)) {
                    continue;
                }

                points.add(pnew);
                insertPoint(grid, cellsize, pnew);
                active.add(pnew);
                found = true;
                break;
            }

            /* If no point was found after k tries, remove p */
            if (!found) {
                active.remove(random_index);
            }
        }

        if (points.size() > samples) {
            ArrayList<Point2D.Double> finalPoints = new ArrayList<>();
            finalPoints.add(points.remove(0));
            Collections.shuffle(points, rand);
            for (int i = 0; i < samples - 1; i++) {
                finalPoints.add(points.get(i));
            }
            return finalPoints;
        }

        return points;
    }
    public static void main(String[] args) {
        double width = 0.007614213197969543, height = 0.007614213197969543;
        int N = 289, k = 200;
        List<Point2D.Double> samples = generatePoissonDiskSamples(width, height, N, k);

//        double atan1_2 = 0.463647609;
//        double cosa = Math.cos(atan1_2);
//        double sina = Math.sin(atan1_2);
//        for(int i = 0; i < samples.size(); i++) {
//            Point2D.Double p = samples.get(i);
//            double x = p.x - width/2;
//            double y = p.y - height/2;
//            p.x = x * cosa - y * sina;
//            p.y = x * sina + y * cosa;
//            p.x += width / 2;
//            p.y += height / 2;
//        }

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

