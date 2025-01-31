package fractalzoomer.utils.space_filling_curves;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

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

    static class UnionFind {
        private int[] parent;
        private int[] rank;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];

            // Initialize each node as its own parent (self-loop)
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        // Find the root of a set with path compression
        public int find(int x) {
            int parent_x = parent[x];
            if (parent_x != x) {
                parent[x] = find(parent_x);  // Path compression
            }
            return parent[x];
        }

        public void compress() {
            for(int x = 0; x < parent.length; x++) {
                find(x);
            }
        }

        public boolean isConnected() {
            compress();

            if(parent.length == 0) {
                return false;
            }

            int parentStart = parent[0];
            for(int x = 1; x < parent.length; x++) {
                if(parent[x] != parentStart) {
                    return false;
                }
            }

            return true;
        }

        // Union two sets based on rank
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }

        // Check if two nodes are in the same set
        public boolean connected(int a, int b) {
            return find(a) == find(b);
        }
    }


    public static ArrayList<Edge> findSpanningTree(List<Edge> edges, int numberOfPoints, int width) {

        UnionFind uf = new UnionFind(numberOfPoints);
        ArrayList<Edge> spanningTree = new ArrayList<>();

        for (Edge edge : edges) {
            int index1 = edge.child.y * width + edge.child.x;
            int index2 = edge.parent.y * width + edge.parent.x;

            if (!uf.connected(index1, index2)) {
                uf.union(index1, index2);
                spanningTree.add(edge);

                // If we have added n-1 edges, we can stop (where n is the number of vertices)
                if (spanningTree.size() == numberOfPoints - 1) {
                    break;
                }
            }
        }

        return spanningTree;
    }

    public static Object[] generateKruskalsRandom(int width, int height, Random r) {
        ArrayList<Edge> all_edges = new ArrayList<>();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(x + 1 < width) {
                    all_edges.add(new Edge(new Point(x, y), new Point(x + 1, y)));
                }
                if(x - 1 >= 0) {
                    all_edges.add(new Edge(new Point(x, y), new Point(x - 1, y)));
                }
                if(y + 1 < height) {
                    all_edges.add(new Edge(new Point(x, y), new Point(x, y + 1)));
                }
                if(y - 1 >= 0) {
                    all_edges.add(new Edge(new Point(x, y), new Point(x, y - 1)));
                }
            }
        }

        Collections.shuffle(all_edges, r);

        ArrayList<Edge> mst = findSpanningTree(all_edges, width * height, width);
        return new Object[] {null, mst};
    }

    public static boolean DFS = true;
    public static boolean START_AT_ZERO = false;

    public static Object[] generateHybrid(int width, int height, Random r, int algorithm) {
        if(algorithm < 0 || algorithm > 9){
            throw new UnsupportedOperationException();
        }

        boolean[] visited = new boolean[width * height];
        boolean[] added = new boolean[width * height];
        int startx = START_AT_ZERO ? 0 : r.nextInt(width);
        int starty = START_AT_ZERO ? 0 : r.nextInt(height);

        ArrayList<Edge> edges = new ArrayList<>();

        ArrayList<Edge> active = new ArrayList<>();
        active.add(new Edge(null, new Point(startx, starty)));
        added[starty * width + startx] = true;

        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(Direction.UP);
        directions.add(Direction.DOWN);
        directions.add(Direction.LEFT);
        directions.add(Direction.RIGHT);

        while (!active.isEmpty()) {
            int index = 0;
            switch (algorithm) {
                case 0://backtracker
                case 1://random prims
                case 2: //backtracker/random prims 0: (100/0) 1: (0/100) (2: 75/25) (3: 50/50) (4: 25/75) split
                case 3:
                case 4:
                default:
                    double split_threshold = 1;
                    if(algorithm == 1) {
                        split_threshold = 0;
                    }
                    else if(algorithm == 2) {
                        split_threshold = 0.75;
                    }
                    else if(algorithm == 3) {
                        split_threshold = 0.5;
                    }
                    else if(algorithm == 4) {
                        split_threshold = 0.25;
                    }

                    if(r.nextDouble() < split_threshold || algorithm == 0){
                        index = active.size() - 1;
                    }
                    else {
                        index = r.nextInt(active.size());
                    }
                    break;
                case 5: //Oldest
                    index = 0;
                    break;
                case 6: //Middle
                    index = active.size() / 2;
                    break;
                case 7: //Newest/Oldest 50/50
                    if(r.nextDouble() < 0.5) {
                        index = active.size() - 1;
                    }
                    else {
                        index = 0;
                    }
                    break;
                case 8: //Oldest/Random 50/50
                    if(r.nextDouble() < 0.5) {
                        index = 0;
                    }
                    else {
                        index = r.nextInt(active.size());
                    }
                    break;
                case 9: //All 0.25%
                    double val = r.nextDouble();
                    if(val < 0.25) {
                        index = 0;//oldest
                    }
                    else if (val < 0.5) {
                        index = active.size() - 1;//newest
                    }
                    else if (val < 0.75) {
                        index = r.nextInt(active.size());//random
                    }
                    else {
                        index = active.size() / 2; //mid
                    }
                    break;
            }

            Edge currentEdge = active.get(index);
            Point current = currentEdge.child;
            int loc = current.y * width + current.x;

            if (!visited[loc]) {
                edges.add(currentEdge);
            }
            visited[loc] = true;

            if(!(current.y - 1 >= 0 && !visited_or_added(visited, added,loc - width) ||
                    current.y + 1 < height && !visited_or_added(visited, added,loc + width) ||
                    current.x - 1 >= 0 && !visited_or_added(visited, added,loc - 1) ||
                    current.x + 1 < width && !visited_or_added(visited, added,loc + 1))
            ) {
                active.remove(index);
                continue;
            }

            int count = 0;
            do {
                int dir_index = r.nextInt(directions.size());
                Direction d = directions.get(dir_index);
                if (d == Direction.UP && current.y - 1 >= 0 && !visited_or_added(visited, added,loc - width)) {
                    active.add(new Edge(current, new Point(current.x, current.y - 1)));
                    count++;
                    added[loc - width] = true;
                } else if (d == Direction.DOWN && current.y + 1 < height && !visited_or_added(visited, added,loc + width)) {
                    active.add(new Edge(current, new Point(current.x, current.y + 1)));
                    count++;
                    added[loc + width] = true;
                } else if (d == Direction.LEFT && current.x - 1 >= 0 && !visited_or_added(visited, added,loc - 1)) {
                    active.add(new Edge(current, new Point(current.x - 1, current.y)));
                    count++;
                    added[loc - 1] = true;
                } else if (d == Direction.RIGHT && current.x + 1 < width && !visited_or_added(visited, added, loc + 1)) {
                    active.add(new Edge(current, new Point(current.x + 1, current.y)));
                    count++;
                    added[loc + 1] = true;
                }
            } while (count == 0);
        }

        return new Object[] {new Point(startx, starty), edges};
    }

    private static boolean visited_or_added(boolean[] visited, boolean[] added, int loc) {
        return visited[loc] || added[loc];
    }

    public static Object[] generateRandomPrims(int width, int height, Random r) {
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

            int count = 0;
            do {
                int dir_index = r.nextInt(directions.size());
                Direction d = directions.get(dir_index);
                if (d == Direction.UP && current.y - 1 >= 0 && !visited[loc - width]) {
                    active.add(new Edge(current, new Point(current.x, current.y - 1)));
                    count++;
                } else if (d == Direction.DOWN && current.y + 1 < height && !visited[loc + width]) {
                    active.add(new Edge(current, new Point(current.x, current.y + 1)));
                    count++;
                } else if (d == Direction.LEFT && current.x - 1 >= 0 && !visited[loc - 1]) {
                    active.add(new Edge(current, new Point(current.x - 1, current.y)));
                    count++;
                } else if (d == Direction.RIGHT && current.x + 1 < width && !visited[loc + 1]) {
                    active.add(new Edge(current, new Point(current.x + 1, current.y)));
                    count++;
                }
            } while (count == 0);

        }

        return new Object[] {new Point(startx, starty), edges};
    }

    public static Object[] generateHuntAndKill(int width, int height, Random r, int algorithm) {

        if(algorithm < 0 || algorithm > 3){
            throw new UnsupportedOperationException();
        }

        boolean[] visited = new boolean[width * height];
        int startx = START_AT_ZERO ? 0 : r.nextInt(width);
        int starty = START_AT_ZERO ? 0 : r.nextInt(height);

        ArrayList<Edge> edges = new ArrayList<>();

        Stack<Edge> stack = new Stack<>();
        stack.add(new Edge(null, new Point(startx, starty)));

        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(Direction.UP);
        directions.add(Direction.DOWN);
        directions.add(Direction.LEFT);
        directions.add(Direction.RIGHT);

        boolean should_continue = false;

        do {

            while (!stack.isEmpty()) {

                Edge currentEdge = stack.pop();
                Point current = currentEdge.child;
                int loc = current.y * width + current.x;

                if (!visited[loc]) {
                    edges.add(currentEdge);
                }
                visited[loc] = true;

                if (!(current.y - 1 >= 0 && !visited[loc - width] ||
                        current.y + 1 < height && !visited[loc + width] ||
                        current.x - 1 >= 0 && !visited[loc - 1] ||
                        current.x + 1 < width && !visited[loc + 1])
                ) {
                    continue;
                }

                int count = 0;
                do {
                    int dir_index = r.nextInt(directions.size());
                    Direction d = directions.get(dir_index);
                    if (d == Direction.UP && current.y - 1 >= 0 && !visited[loc - width]) {
                        stack.add(new Edge(current, new Point(current.x, current.y - 1)));
                        count++;
                    } else if (d == Direction.DOWN && current.y + 1 < height && !visited[loc + width]) {
                        stack.add(new Edge(current, new Point(current.x, current.y + 1)));
                        count++;
                    } else if (d == Direction.LEFT && current.x - 1 >= 0 && !visited[loc - 1]) {
                        stack.add(new Edge(current, new Point(current.x - 1, current.y)));
                        count++;
                    } else if (d == Direction.RIGHT && current.x + 1 < width && !visited[loc + 1]) {
                        stack.add(new Edge(current, new Point(current.x + 1, current.y)));
                        count++;
                    }
                } while (count == 0);



            }

            int beginy = 0;
            int limity = height;

            int beginx = 0;
            int limitx = width;

            int incx = 1;
            int incy = 1;

            if(algorithm == 1 || algorithm == 3) {
                beginx = width - 1;
                limitx = -1;
                incx = -1;
            }

            if(algorithm == 2 || algorithm == 3) {
                beginy = height - 1;
                limity = -1;
                incy = -1;
            }

            should_continue = false;

            for(int y = beginy; y != limity; y += incy) {
                for(int x = beginx; x != limitx; x += incx) {
                    int loc = y * width + x;

                    if(!visited[loc]) {
                        ArrayList<Integer> visited_locs = new ArrayList<>();

                        if((y - 1 >= 0 && visited[loc - width])) {
                            visited_locs.add(loc - width);
                        }

                        if(y + 1 < height && visited[loc + width]) {
                            visited_locs.add(loc + width);
                        }

                        if(x - 1 >= 0 && visited[loc - 1]) {
                            visited_locs.add(loc - 1);
                        }

                        if(x + 1 < width && visited[loc + 1]) {
                            visited_locs.add(loc + 1);
                        }

                        if(visited_locs.isEmpty()) {
                            continue;
                        }

                        int index = r.nextInt(visited_locs.size());

                        int new_loc = visited_locs.get(index);
                        int parent_y = new_loc / width;
                        int parent_x = new_loc % width;
                        stack.add(new Edge(new Point(parent_x, parent_y), new Point(x, y)));

                        should_continue = true;
                        break;
                    }
                }

                if(should_continue) {
                    break;
                }
            }
        } while (should_continue);

        return new Object[] {new Point(startx, starty), edges};
    }

    public static Object[] generateBinaryTree(int width, int height, Random r, int algorithm) {

        if(algorithm < 0 || algorithm > 3){
            throw new UnsupportedOperationException();
        }

        boolean[] visited = new boolean[width * height];

        ArrayList<Edge> edges = new ArrayList<>();

        int incy = 1;
        int incx = 1;

        int ystart = 0;
        int ylimit = height;
        if(algorithm == 1 || algorithm == 3) {
            ystart = height - 1;
            ylimit = -1;
            incy = -1;
        }

        int xstart = 0;
        int xlimit = width;
        if(algorithm == 2 || algorithm == 3) {
            xstart = width - 1;
            xlimit = -1;
            incx = -1;
        }

        for(int x = xstart; x != xlimit; x += incx) {
            for(int y = ystart; y != ylimit; y += incy) {

                int loc = y * width + x;

                if(visited[loc]) {
                    continue;
                }

                visited[loc] = true;

                ArrayList<Direction> directions = new ArrayList<>();
                if(algorithm == 0) {
                    if (y + 1 < height && !visited[loc + width]) {
                        directions.add(Direction.DOWN);
                    }
                    if (x + 1 < width && !visited[loc + 1]) {
                        directions.add(Direction.RIGHT);
                    }
                }
                else if(algorithm == 1) {
                    if (y - 1 >= 0 && !visited[loc - width]) {
                        directions.add(Direction.UP);
                    }
                    if (x + 1 < width && !visited[loc + 1]) {
                        directions.add(Direction.RIGHT);
                    }
                }
                else if(algorithm == 2) {
                    if (y + 1 < height && !visited[loc + width]) {
                        directions.add(Direction.DOWN);
                    }
                    if (x - 1 >= 0 && !visited[loc - 1]) {
                        directions.add(Direction.LEFT);
                    }
                }
                else {
                    if (y - 1 >= 0 && !visited[loc - width]) {
                        directions.add(Direction.UP);
                    }
                    if (x - 1 >= 0 && !visited[loc - 1]) {
                        directions.add(Direction.LEFT);
                    }
                }

                if(directions.isEmpty()) {
                    continue;
                }

                int index = r.nextInt(directions.size());
                Direction dir = directions.get(index);
                if (dir == Direction.UP) {
                    edges.add(new Edge(new Point(x, y), new Point(x, y - 1)));
                }
                else if (dir == Direction.DOWN) {
                    edges.add(new Edge(new Point(x, y), new Point(x, y + 1)));
                }
                else if (dir == Direction.LEFT) {
                    edges.add(new Edge(new Point(x, y), new Point(x - 1, y)));
                }
                else if (dir == Direction.RIGHT) {
                    edges.add(new Edge(new Point(x, y), new Point(x + 1, y)));
                }
            }
        }

        Point start;
        if(algorithm == 0) {
            start = new Point(0 ,0);
        }
        else if(algorithm == 1) {
            start = new Point(0 ,height - 1);
        }
        else if(algorithm == 2) {
            start = new Point(width - 1 ,0);
        }
        else  {
            start = new Point(width - 1 ,height - 1);
        }
        return new Object[] {start, edges};
    }

    public static Object[] generateAldousBroder(int width, int height, Random r) {
        boolean[] visited = new boolean[width * height];
        int startx = START_AT_ZERO ? 0 : r.nextInt(width);
        int starty = START_AT_ZERO ? 0 : r.nextInt(height);

        ArrayList<Edge> edges = new ArrayList<>();

        int x = startx;
        int y = starty;
        int loc = y * width + x;
        visited[loc] = true;
        int total_visited = 1;

        while (true) {

            ArrayList<Direction> directions = new ArrayList<>();

            if (y - 1 >= 0) {
                directions.add(Direction.UP);
            }
            if (x - 1 >= 0) {
                directions.add(Direction.LEFT);
            }
            if (x + 1 < width) {
                directions.add(Direction.RIGHT);
            }
            if (y + 1 < height) {
                directions.add(Direction.DOWN);
            }

            if(directions.isEmpty()) {
                break;
            }

            int index = r.nextInt(directions.size());

            int parent_x = x;
            int parent_y = y;

            Direction dir = directions.get(index);
            if(dir == Direction.UP) {
                y--;
            }
            else if(dir == Direction.DOWN) {
                y++;
            }
            else if(dir == Direction.LEFT) {
                x--;
            }
            else {
                x++;
            }

            loc = y * width + x;

            if(!visited[loc]) {
                edges.add(new Edge(new Point(parent_x, parent_y), new Point(x, y)));
                visited[loc] = true;
                total_visited++;
            }

            if(total_visited == width * height) {
                break;
            }
        }


        return new Object[] {new Point(startx, starty), edges};
    }

    private static void sidewinterStop(ArrayList<Edge> edges, ArrayList<Point> run_set, Random r, int algorithm) {
        for(int i = 0; i < run_set.size() - 1; i++) {
            edges.add(new Edge(run_set.get(i), run_set.get(i + 1)));
        }
        int index = r.nextInt(run_set.size());
        Point p = run_set.get(index);
        if(algorithm == 0) {
            edges.add(new Edge(new Point(p.x, p.y - 1), p));
        }
        else {
            edges.add(new Edge(new Point(p.x, p.y + 1), p));
        }
        run_set.clear();
    }

    public static Object[] generateSidewinder(int width, int height, Random r, int algorithm, double right_percent) {
        if(algorithm < 0 || algorithm > 1){
            throw new UnsupportedOperationException();
        }

        int startx = 0;
        int starty = 0;
        int ylimit = height;
        int yinc = 1;

        if(algorithm == 1) {
            starty = height - 1;
            ylimit = -1;
            yinc = -1;
        }

        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(new Edge(null, new Point(startx, starty)));

        for(int x = startx; x < width - 1; x++) {
            edges.add(new Edge(new Point(x, starty), new Point(x + 1, starty)));
        }

        ArrayList<Point> run_set = new ArrayList<>();

        for(int y = starty + yinc; y != ylimit; y += yinc) {
            for(int x = startx; x < width; x++) {
                run_set.add(new Point(x, y));
                boolean go_right = r.nextDouble() < right_percent;
                if(!go_right) {
                    sidewinterStop(edges, run_set, r, algorithm);
                }
            }
            if(!run_set.isEmpty()) {
                sidewinterStop(edges, run_set, r, algorithm);
            }
        }

        return new Object[] {new Point(startx, starty), edges};
    }

    private static void EllerStop(ArrayList<Integer>[] sets, ArrayList<Integer>[] new_sets , ArrayList<Point> mergedPoints, ArrayList<Edge> edges, Random r, int algorithm, boolean addVertical, double down_percent, int max_down_connections) {

        for (int i = 0; i < mergedPoints.size() - 1; i++) {
            edges.add(new Edge(mergedPoints.get(i), mergedPoints.get(i + 1)));
        }

        int firstSetIndex = mergedPoints.get(0).x;
        ArrayList<ArrayList<Integer>> merge_sets = new ArrayList<>();
        for (int i = 1; i < mergedPoints.size(); i++) {
            int index = mergedPoints.get(i).x;
            sets[firstSetIndex].addAll(sets[index]);
            merge_sets.add(sets[index]);
        }

        for(int i = 0; i < merge_sets.size(); i++) {
            for (int j = 0; j < sets.length; j++) {
                if(sets[j] != null && sets[j] == merge_sets.get(i)) {
                    sets[j] = sets[firstSetIndex];
                }
                if(new_sets[j] != null && new_sets[j] == merge_sets.get(i)) {
                    new_sets[j] = sets[firstSetIndex];
                }
            }
        }

        if(addVertical) {
            if(mergedPoints.size() == 1) {
                Point p = mergedPoints.get(0);
                if (algorithm == 0) {
                    edges.add(new Edge(p, new Point(p.x, p.y + 1)));
                } else {
                    edges.add(new Edge(p, new Point(p.x, p.y - 1)));
                }
                new_sets[p.x] = sets[firstSetIndex];
            }
            else {
                double val = r.nextDouble();
                max_down_connections = max_down_connections <= 0 ? 1 : max_down_connections;
                max_down_connections = Math.min(max_down_connections, mergedPoints.size());
                int number = val < down_percent ? r.nextInt(max_down_connections) + 1 : 1;
                ArrayList<Point> list = new ArrayList<>(mergedPoints);
                for(int i = 0; i < number; i++) {
                    int index = r.nextInt(list.size());
                    Point p = list.remove(index);
                    if (algorithm == 0) {
                        edges.add(new Edge(p, new Point(p.x, p.y + 1)));
                    } else {
                        edges.add(new Edge(p, new Point(p.x, p.y - 1)));
                    }

                    new_sets[p.x] = sets[firstSetIndex];
                }
            }

        }
        mergedPoints.clear();

    }

    public static Object[] generateEller(int width, int height, Random r, int algorithm, double right_percent, double down_percent, int max_down_connections) {

        if(algorithm < 0 || algorithm > 1){
            throw new UnsupportedOperationException();
        }

        int startx = 0;
        int starty = 0;
        int ylimit = height;
        int ylimit2 = height - 1;
        int yinc = 1;

        if(algorithm == 1) {
            starty = height - 1;
            ylimit = -1;
            ylimit2 = 0;
            yinc = -1;
        }

        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(new Edge(null, new Point(startx, starty)));

        ArrayList<Integer>[] sets = new ArrayList[width];
        for(int i = 0; i < sets.length; i++) {
            sets[i] = new ArrayList<>();
        }

        for (int x = startx; x < width; x++) {
            sets[x].add(x);
        }

        for(int y = starty; y != ylimit; y += yinc) {

            ArrayList<Integer>[] new_sets = new ArrayList[sets.length];

            ArrayList<ArrayList<Integer>> adjacent_sets = new ArrayList<>();
            ArrayList<Point> mergedPoints = new ArrayList<>();
            for (int x = startx; x < width;) {

                boolean go_right = false;
                boolean do_increment = true;
                if(!adjacent_sets.contains(sets[x])) {
                    mergedPoints.add(new Point(x, y));
                    adjacent_sets.add(sets[x]);
                    go_right = y == ylimit2 || r.nextDouble() < right_percent;
                }
                else {
                    do_increment = false;
                }

                if (!go_right) {
                    EllerStop(sets, new_sets, mergedPoints, edges, r, algorithm, y != ylimit2, down_percent, max_down_connections);
                    adjacent_sets.clear();
                }

                if(do_increment) {
                    x++;
                }
            }

            if (!mergedPoints.isEmpty()) {
                EllerStop(sets, new_sets, mergedPoints, edges, r, algorithm, y != ylimit2, down_percent, max_down_connections);
                adjacent_sets.clear();
            }

            sets = new_sets;

            for(int x = startx; x < width; x++) {
                if(sets[x] == null) {
                    sets[x] = new ArrayList<>();
                    sets[x].add(x);
                }
            }
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

        BufferedImage a = new BufferedImage(1200, 1200, BufferedImage.TYPE_INT_ARGB);

        int size = 80;

        int width = size;
        int height = size;

        Object[] data =
                //generateKruskalsRandom(width, height, new Random(1));
                //generateRandomPrims(width, height, new Random(1));
                generateBacktracking(width, height, new Random(1));
                //generateSidewinder(width, height, new Random(1), 0, 0.5);
                //generateEller(width, height, new Random(1), 0, 0.52, 0.01, 5);
                //generateHuntAndKill(width, height, new Random(1), 0);
                //generateBinaryTree(width, height, new Random(1), 0);
        ArrayList<Edge> edges = (ArrayList<Edge>) data[1];
        Point start = (Point) data[0];

        Graphics2D g2d = a.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, a.getWidth(), a.getHeight());

        RenderFrame frame = new RenderFrame(a);

        boolean drawRoot = true;

        if(drawCircles || drawRoot) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        int vertical_edges = 0;
        int horizontal_edges = 0;

        if(changeStroke && !drawCircles) {
            g2d.setStroke(new BasicStroke(offset / 2));
        }

        double max_distance = width* width + height * height;
        boolean drawCircularDistanceColor = false;

        UnionFind uf = new UnionFind(width * height);

        for(int i = 0; i < edges.size(); i++) {

            Point p2 = edges.get(i).child;

            if(p2 == null) {
                continue;
            }

            if(drawColor) {
                if(drawCircularDistanceColor) {
                    double distance = (p2.x - start.x) * (p2.x - start.x) + (p2.y - start.y) * (p2.y - start.y);
                    g2d.setColor(new Color(palette[(int) ((distance / max_distance) * (palette.length - 1))]));
                }
                else {
                    g2d.setColor(new Color(palette[(int) ((i / (double) edges.size()) * (palette.length - 1))]));
                }
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

            int index1 = y1 * width + x1;
            int index2 = y2 * width + x2;

            if (!uf.connected(index1, index2)) {
                uf.union(index1, index2);
            }
            else {
                System.err.println("The maze has cycles");
            }

            boolean isRoot = start != null && (x1 == start.x && y1 == start.y || x2 == start.x && y2 == start.y);

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

            frame.repaint();

        }

        g2d.dispose();

        if(!uf.isConnected()) {
            System.err.println("The maze is disconnected");
        }

        System.out.println("Vertical Edges: " + vertical_edges);
        System.out.println("Horizontal Edges: " + horizontal_edges);

        ImageIO.write(a, "png", new File("maze.png"));

    }
}
