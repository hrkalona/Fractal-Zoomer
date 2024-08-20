
package fractalzoomer.utils;

/**
 *
 * @author hrkalona2
 */
public class Square {
    public final int x1;
    public final int y1;
    public final int x2;
    public final int y2;
    public final int iteration;
    
    public Square(int x1, int y1, int x2, int y2, int iteration) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.iteration = iteration;
    }

    public Square(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        iteration = 0;
    }
    
    @Override
    public String toString() {
        return "X1: " + x1 + " Y1: " + y1 + " X2: " + x2 + " Y2: " + y2;
    }

    public boolean isValid() {
        if(x1 != x2 && y1 != y2) {
            return true;
        }
        else if(x1 == x2 && y1 != y2) {
            return true;
        }
        else return x1 != x2;
    }
}
