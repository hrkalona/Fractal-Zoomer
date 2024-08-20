package fractalzoomer.utils.space_filling_curves;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static fractalzoomer.utils.space_filling_curves.CurveData.*;

public class MazeCurve {

   static class Point {

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        int x;
        int y;
    }

    static class Edge {
       public Edge(Point parent, Point child) {
           this.parent = parent;
           this.child = child;
       }
       Point parent;
       Point child;
    }

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public static boolean DFS = true;
    public static boolean START_AT_ZERO = true;

    public static Object[] generatePrims(int width, int height, Random r) {
        boolean[] visited = new boolean[width * height];
        int startx = START_AT_ZERO ? 0 : r.nextInt(width);
        int starty = START_AT_ZERO ? 0 : r.nextInt(height);

        ArrayList<Edge> edges = new ArrayList<>();

        ArrayList<Edge> active = new ArrayList<>();
        active.add(new Edge(null, new Point(startx, starty)));

        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(Direction.UP);
        directions.add(Direction.DOWN);
        directions.add(Direction.LEFT);
        directions.add(Direction.RIGHT);

        while (!active.isEmpty()) {
            int index = r.nextInt(active.size());

            Edge currentEdge = active.get(index);
            Point current = currentEdge.child;
            int loc = current.y * width + current.x;

            if (!visited[loc]) {
                edges.add(currentEdge);
            }
            visited[loc] = true;

            if(!(current.y - 1 >= 0 && !visited[loc - width] ||
                    current.y + 1 < height && !visited[loc + width] ||
                    current.x - 1 >= 0 && !visited[loc - 1] ||
                    current.x + 1 < width && !visited[loc + 1])
            ) {
                active.remove(index);
                continue;
            }

            boolean added = false;
            do {
                int dir_index = r.nextInt(directions.size());
                Direction d = directions.get(dir_index);
                if (d == Direction.UP && current.y - 1 >= 0 && !visited[loc - width]) {
                    active.add(new Edge(current, new Point(current.x, current.y - 1)));
                    added = true;
                } else if (d == Direction.DOWN && current.y + 1 < height && !visited[loc + width]) {
                    active.add(new Edge(current, new Point(current.x, current.y + 1)));
                    added = true;
                } else if (d == Direction.LEFT && current.x - 1 >= 0 && !visited[loc - 1]) {
                    active.add(new Edge(current, new Point(current.x - 1, current.y)));
                    added = true;
                } else if (d == Direction.RIGHT && current.x + 1 < width && !visited[loc + 1]) {
                    active.add(new Edge(current, new Point(current.x + 1, current.y)));
                    added = true;
                }
            } while (!added);
        }

        return new Object[] {new Point(startx, starty), edges};
    }

    public static Object[] generateBacktracking(int width, int height, Random r) {
        boolean[] visited = new boolean[width * height];
        int startx = START_AT_ZERO ? 0 : r.nextInt(width);
        int starty = START_AT_ZERO ? 0 : r.nextInt(height);

        ArrayList<Edge> edges = new ArrayList<>();

        Stack<Edge> stack = new Stack<>();
        Queue<Edge> queue = new LinkedList<>();

        if(DFS) {
            stack.add(new Edge(null, new Point(startx, starty)));
        }
        else {
            queue.add(new Edge(null, new Point(startx, starty)));
        }

        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(Direction.UP);
        directions.add(Direction.DOWN);
        directions.add(Direction.LEFT);
        directions.add(Direction.RIGHT);

        while (DFS ? !stack.isEmpty() : !queue.isEmpty()) {
            Edge currentEdge = DFS ? stack.pop() : queue.poll();
            Point current = currentEdge.child;
            int loc = current.y * width + current.x;
            if(visited[loc]) {
                continue;
            }
            visited[loc] = true;

            edges.add(currentEdge);

            Collections.shuffle(directions, r);

            directions.forEach(d -> {
                if(d == Direction.UP && current.y - 1 >= 0 && !visited[loc - width]) {
                    if(DFS) {
                        stack.add(new Edge(current, new Point(current.x, current.y - 1)));
                    }
                    else {
                        queue.add(new Edge(current, new Point(current.x, current.y - 1)));
                    }
                }
                else if(d == Direction.DOWN && current.y + 1 < height && !visited[loc + width]) {
                    if(DFS) {
                        stack.add(new Edge(current, new Point(current.x, current.y + 1)));
                    }
                    else {
                        queue.add(new Edge(current, new Point(current.x, current.y + 1)));
                    }
                }
                else if(d == Direction.LEFT && current.x - 1 >= 0 && !visited[loc - 1]) {
                    if(DFS) {
                        stack.add(new Edge(current, new Point(current.x - 1, current.y)));
                    }
                    else {
                        queue.add(new Edge(current, new Point(current.x - 1, current.y)));
                    }
                }
                else if(d == Direction.RIGHT && current.x + 1 < width && !visited[loc + 1]) {
                    if(DFS) {
                        stack.add(new Edge(current, new Point(current.x + 1, current.y)));
                    }
                    else {
                        queue.add(new Edge(current, new Point(current.x + 1, current.y)));
                    }
                }
            });
        }

        return new Object[] {new Point(startx, starty), edges};
    }

    public static void main(String[] args) throws IOException {

        BufferedImage a = new BufferedImage(1700, 1700, BufferedImage.TYPE_INT_ARGB);

        int size = 75;

        Object[] data = generatePrims(size, size, new Random(1));
        ArrayList<Edge> edges = (ArrayList<Edge>) data[1];
        Point start = (Point) data[0];

        Graphics2D g2d = a.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, a.getWidth(), a.getHeight());

        boolean drawRoot = true;

        if(drawCircles || drawRoot) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        int vertical_edges = 0;
        int horizontal_edges = 0;

        if(changeStroke && !drawCircles) {
            g2d.setStroke(new BasicStroke(offset / 2));
        }

        double max_distance = Math.sqrt(2 * size * size);

        for(int i = 0; i < edges.size(); i++) {

            Point p2 = edges.get(i).child;

            if(p2 == null) {
                continue;
            }

            if(drawColor) {
                double distance = Math.sqrt((p2.x - start.x) * (p2.x - start.x) + (p2.y - start.y) * (p2.y - start.y));
                g2d.setColor(new Color(palette[(int) ((distance / max_distance) * (palette.length - 1))]));
            }
            else {
                g2d.setColor(Color.WHITE);
            }

            Point p1 = edges.get(i).parent;

            if(p1 == null) {
                continue;
            }

            int x1 = p1.x;
            int y1 = p1.y;
            int x2 = p2.x;
            int y2 = p2.y;

            boolean isRoot = x1 == start.x && y1 == start.y;

            int p1x = x1 * offset + offset;
            int p1y = y1 * offset + offset;
            int p2x = x2 * offset + offset;
            int p2y = y2 * offset + offset;

            if(Math.abs(x1 - x2) > 1 || Math.abs(y1 - y2) > 1) {
                System.err.println("Non consecutive");
            }

            if(x1 == x2) {
                vertical_edges++;
            }
            else {
                horizontal_edges++;
            }

            int radius = offset / 2;
            if(radius % 2 == 0 ) {
                radius--;
            }
            if(drawCircles) {
                g2d.fillOval(p1x - radius / 2, p1y - radius / 2, radius, radius);
                g2d.fillOval(p2x - radius / 2, p2y - radius / 2, radius, radius);
            }

            g2d.drawLine(p1x, p1y, p2x, p2y);

            if(drawRoot && isRoot) {
                g2d.setColor(Color.WHITE);
                g2d.fillOval(p1x - radius / 2, p1y - radius / 2, radius, radius);
            }
        }

        g2d.dispose();

        System.out.println("Vertical Edges: " + vertical_edges);
        System.out.println("Horizontal Edges: " + horizontal_edges);

        ImageIO.write(a, "png", new File("maze.png"));

    }
}
