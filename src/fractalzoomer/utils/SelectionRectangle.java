package fractalzoomer.utils;

import fractalzoomer.main.MainWindow;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class SelectionRectangle {

    private static final Cursor ROTATE_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(MainWindow.getIcon("rotate.gif").getImage(), new Point(16, 16), "rotate");
    private static final int[] RESIZE_CURSORS = new int[] { Cursor.N_RESIZE_CURSOR, Cursor.NW_RESIZE_CURSOR, Cursor.W_RESIZE_CURSOR,
            Cursor.SW_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR,
            Cursor.E_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR };

    private static final int SELECTION_NODE_RADIUS = 3;
    private static final int SELECTION_ROTATE_BAR_LENGTH = 25;

    private static final int SELECTION_MOUSE_TOLERANCE = 6;

    private static final Stroke SELECTION_STOKE_3 = new BasicStroke(3);

    private static final Stroke SELECTION_STOKE_1 = new BasicStroke(1);

    private static enum SelectionMode { NONE, WAIT, RESIZE, MOVE, ROTATE }

    private int[] selX = new int[6];
    private int[] selY = new int[6];

    private int selCenterX, selCenterY, selLastX, selLastY;
    private double selHalfWidth, selHalfHeight, selRotation;
    private double cosSelRotation;
    private double sinSelRotation;
    private SelectionMode selMode;
    private MainWindow ptr;

    public SelectionRectangle(MainWindow ptr) {
        selMode = SelectionMode.NONE;
        this.ptr = ptr;
    }

    public int getSelectionX() {
        return selCenterX;
    }

    public int getSelectionY() {
        return selCenterY;
    }

    public double getSelectionWidth() {
        return selHalfWidth * 2;
    }

    public double getSelectionHeight() {
        return selHalfHeight * 2;
    }

    public double getSelectionRotation() {
        return selRotation;
    }

    public void setSelectionCenter(int centerX, int centerY) {
        selHalfWidth=selHalfHeight=0;
        selRotation = 0;
        cosSelRotation = 1;
        sinSelRotation = 0;
        selCenterX = centerX;
        selCenterY = centerY;
        selMode = SelectionMode.RESIZE;
        repaintSelectionRectangle();
    }
    public void setSelectionEdge(int x, int y, int image_width, int image_height) {
        int edgeX = transformSelX(x,y);
        int edgeY = transformSelY(x,y);

        double aspectRatio = ((double)image_width)/image_height;
        selHalfWidth = Math.abs(edgeX-selCenterX);
        selHalfHeight = Math.abs(edgeY-selCenterY);
        if(selHalfHeight*aspectRatio > selHalfWidth) {
            selHalfWidth = Math.round(selHalfHeight*aspectRatio);
        }
        else {
            selHalfHeight = Math.round(selHalfWidth/aspectRatio);
        }

        selMode = SelectionMode.RESIZE;
        repaintSelectionRectangle();
    }

    void clearSelection(boolean repaint) {
        selMode = SelectionMode.NONE;
        if(repaint) repaintSelectionRectangle();
    }

    private int transformSelX(double x, double y) {
        return (int)Math.round(selCenterX + ((x - selCenterX) * cosSelRotation + (selCenterY - y) * sinSelRotation));
    }

    public boolean isInRotateArea(Point p) {
        return rotateArea.contains(p);
    }

    private boolean isInMoveArea(Point p) {
        return moveArea.contains(p);
    }

    public boolean isInRectangleArea(Point p) {
        p = transformSelPoint(p);
        return rectangleArea.contains(p);
    }

    private boolean isInResizeArea(Point p) {
        return resizeArea.contains(p) && !isInMoveArea(p);
    }

    public boolean isInResizeAreaS(Point p) {
        return resizeAreaS.contains(p);
    }

    public boolean isInResizeAreaN(Point p) {
        return resizeAreaN.contains(p);
    }

    public boolean isInResizeAreaW(Point p) {
        return resizeAreaW.contains(p);
    }

    public boolean isInResizeAreaE(Point p) {
        return resizeAreaE.contains(p);
    }

    public boolean isInResizeAreaSW(Point p) {
        return resizeAreaSW.contains(p);
    }

    public boolean isInResizeAreaSE(Point p) {
        return resizeAreaSE.contains(p);
    }

    public boolean isInResizeAreaNW(Point p) {
        return resizeAreaNW.contains(p);
    }

    public boolean isInResizeAreaNE(Point p) {
        return resizeAreaNE.contains(p);
    }

    private int transformSelY(double x, double y) {
        return (int)Math.round(selCenterY + ((selCenterY - y) * cosSelRotation + (selCenterX - x) * sinSelRotation));
    }

    private void repaintSelectionRectangle() {
        selX[0] = transformSelX(selCenterX-selHalfWidth,selCenterY-selHalfHeight);
        selX[1] = transformSelX(selCenterX+selHalfWidth,selCenterY-selHalfHeight);
        selX[2] = transformSelX(selCenterX+selHalfWidth,selCenterY+selHalfHeight);
        selX[3] = transformSelX(selCenterX-selHalfWidth,selCenterY+selHalfHeight);
        selX[4] = transformSelX(selCenterX,selCenterY+selHalfHeight+Math.min(selHalfHeight,SELECTION_ROTATE_BAR_LENGTH));
        selX[5] = transformSelX(selCenterX,selCenterY+selHalfHeight+1);

        selY[0] = transformSelY(selCenterX-selHalfWidth,selCenterY-selHalfHeight);
        selY[1] = transformSelY(selCenterX+selHalfWidth,selCenterY-selHalfHeight);
        selY[2] = transformSelY(selCenterX+selHalfWidth,selCenterY+selHalfHeight);
        selY[3] = transformSelY(selCenterX-selHalfWidth,selCenterY+selHalfHeight);
        selY[4] = transformSelY(selCenterX,selCenterY+selHalfHeight+Math.min(selHalfHeight,SELECTION_ROTATE_BAR_LENGTH));
        selY[5] = transformSelY(selCenterX,selCenterY+selHalfHeight+1);

        int minX = selX[0];
        int maxX = selX[0];
        int minY = selY[0];
        int maxY = selY[0];
        for(int i=1; i<5; i++) {
            minX = Math.min(minX,selX[i]);
            maxX = Math.max(maxX,selX[i]);
            minY = Math.min(minY,selY[i]);
            maxY = Math.max(maxY,selY[i]);
        }

        changeArea = new Rectangle2D.Double(minX-SELECTION_NODE_RADIUS,minY-SELECTION_NODE_RADIUS,maxX-minX+2*SELECTION_NODE_RADIUS+1,maxY-minY+2*SELECTION_NODE_RADIUS+1);
        rectangleArea = new Rectangle2D.Double(selCenterX-selHalfWidth,selCenterY-selHalfHeight,2*(selHalfWidth)+1,2*(selHalfHeight)+1);
        resizeArea = new Rectangle2D.Double(selCenterX-selHalfWidth-SELECTION_MOUSE_TOLERANCE,selCenterY-selHalfHeight-SELECTION_MOUSE_TOLERANCE,2*(selHalfWidth+SELECTION_MOUSE_TOLERANCE)+1,2*(selHalfHeight+SELECTION_MOUSE_TOLERANCE)+1);
        resizeAreaS = new Rectangle2D.Double(selCenterX-selHalfWidth+SELECTION_MOUSE_TOLERANCE,selCenterY-selHalfHeight-SELECTION_MOUSE_TOLERANCE,2*(selHalfWidth-SELECTION_MOUSE_TOLERANCE)+1,2*SELECTION_MOUSE_TOLERANCE+1);
        resizeAreaN = new Rectangle2D.Double(selCenterX-selHalfWidth+SELECTION_MOUSE_TOLERANCE,selCenterY+selHalfHeight-SELECTION_MOUSE_TOLERANCE,2*(selHalfWidth-SELECTION_MOUSE_TOLERANCE)+1,2*SELECTION_MOUSE_TOLERANCE+1);
        resizeAreaW = new Rectangle2D.Double(selCenterX-selHalfWidth-SELECTION_MOUSE_TOLERANCE,selCenterY-selHalfHeight+SELECTION_MOUSE_TOLERANCE,2*SELECTION_MOUSE_TOLERANCE+1,2*(selHalfHeight-SELECTION_MOUSE_TOLERANCE)+1);
        resizeAreaE = new Rectangle2D.Double(selCenterX+selHalfWidth-SELECTION_MOUSE_TOLERANCE,selCenterY-selHalfHeight+SELECTION_MOUSE_TOLERANCE,2*SELECTION_MOUSE_TOLERANCE+1,2*(selHalfHeight-SELECTION_MOUSE_TOLERANCE)+1);
        resizeAreaNE = new Rectangle2D.Double(selCenterX+selHalfWidth-SELECTION_MOUSE_TOLERANCE,selCenterY+selHalfHeight-SELECTION_MOUSE_TOLERANCE,2*SELECTION_MOUSE_TOLERANCE+1,2*SELECTION_MOUSE_TOLERANCE+1);
        resizeAreaNW = new Rectangle2D.Double(selCenterX-selHalfWidth-SELECTION_MOUSE_TOLERANCE,selCenterY+selHalfHeight-SELECTION_MOUSE_TOLERANCE,2*SELECTION_MOUSE_TOLERANCE+1,2*SELECTION_MOUSE_TOLERANCE+1);
        resizeAreaSE = new Rectangle2D.Double(selCenterX+selHalfWidth-SELECTION_MOUSE_TOLERANCE,selCenterY-selHalfHeight-SELECTION_MOUSE_TOLERANCE,2*SELECTION_MOUSE_TOLERANCE+1,2*SELECTION_MOUSE_TOLERANCE+1);
        resizeAreaSW = new Rectangle2D.Double(selCenterX-selHalfWidth-SELECTION_MOUSE_TOLERANCE,selCenterY-selHalfHeight-SELECTION_MOUSE_TOLERANCE,2*SELECTION_MOUSE_TOLERANCE+1,2*SELECTION_MOUSE_TOLERANCE+1);
        moveArea = new Rectangle2D.Double(selCenterX-selHalfWidth+SELECTION_MOUSE_TOLERANCE,selCenterY-selHalfHeight+SELECTION_MOUSE_TOLERANCE,2*(selHalfWidth-SELECTION_MOUSE_TOLERANCE)+1,2*(selHalfHeight-SELECTION_MOUSE_TOLERANCE)+1);
        rotateArea = new Rectangle2D.Double(selX[4]-SELECTION_MOUSE_TOLERANCE,selY[4]-SELECTION_MOUSE_TOLERANCE,2*SELECTION_MOUSE_TOLERANCE+1,2*SELECTION_MOUSE_TOLERANCE+1);
        //repaint(minX-SELECTION_NODE_RADIUS,minY-SELECTION_NODE_RADIUS,maxX-minX+2*SELECTION_NODE_RADIUS+1,maxY-minY+2*SELECTION_NODE_RADIUS+1);
        //if(repaintPreview) repaintSelectionPreview();
    }

    Rectangle2D.Double changeArea;
    Rectangle2D.Double rectangleArea;

    Rectangle2D.Double moveArea;
    Rectangle2D.Double resizeArea;
    Rectangle2D.Double resizeAreaS;
    Rectangle2D.Double resizeAreaN;
    Rectangle2D.Double resizeAreaW;
    Rectangle2D.Double resizeAreaE;
    Rectangle2D.Double resizeAreaNE;
    Rectangle2D.Double resizeAreaNW;

    Rectangle2D.Double resizeAreaSE;

    Rectangle2D.Double resizeAreaSW;

    Rectangle2D.Double rotateArea;

    public boolean isInImageChangeArea(Point p) {
        return changeArea.contains(p);
    }

    public void draw(Graphics2D g2) {

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        boolean paintRotationBar = !ptr.isAutoZoomToSelectionRectangleEnabled();

        Stroke line_stroke = SELECTION_STOKE_1;
        if (MainWindow.zoom_window_style == 0) {
            float[] dash1 = {5.0f};
            line_stroke
                    = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f, dash1, 0.0f);
        }

        //if(selCenterX!=selLastX || selCenterY!=selLastY) {
            g2.setStroke(SELECTION_STOKE_3);
            for(int i=0; i<4; i++) g2.drawLine(selX[i],selY[i],selX[(i+1)%4],selY[(i+1)%4]);
            if (paintRotationBar) g2.drawLine(selX[4],selY[4],selX[5],selY[5]);
            g2.setColor(MainWindow.zoom_window_color);
            g2.setStroke(line_stroke);
            for(int i=0; i<4; i++) g2.drawLine(selX[i],selY[i],selX[(i+1)%4],selY[(i+1)%4]);
            if (paintRotationBar) g2.drawLine(selX[4],selY[4],selX[5],selY[5]);
        //}
        if (paintRotationBar) {
            g2.setColor(Color.BLACK);
            g2.fillOval(selX[4]-SELECTION_NODE_RADIUS,selY[4]-SELECTION_NODE_RADIUS,2*SELECTION_NODE_RADIUS+1,2*SELECTION_NODE_RADIUS+1);
            g2.setColor(MainWindow.zoom_window_color);
            g2.fillOval(selX[4]-SELECTION_NODE_RADIUS+1,selY[4]-SELECTION_NODE_RADIUS+1,2*SELECTION_NODE_RADIUS-1,2*SELECTION_NODE_RADIUS-1);
        }
    }

    public boolean isVisible() {
        return selMode != SelectionMode.NONE;
    }

    public void dragAction(int x, int y, int image_width, int image_height) {
        if(selMode == SelectionMode.RESIZE) {
            clearSelection(true);
            setSelectionEdge(x,y, image_width, image_height);
        } else if(selMode == SelectionMode.MOVE) {
            clearSelection(true);
            selCenterX += x-selLastX;
            selCenterY += y-selLastY;
            selMode = SelectionMode.MOVE;
            repaintSelectionRectangle();
            selLastX = x;
            selLastY = y;
        } else if(selMode == SelectionMode.ROTATE) {
            clearSelection(true);
            selRotation = Math.atan2(selCenterX-x,selCenterY-y);
            cosSelRotation = Math.cos(selRotation);
            sinSelRotation = Math.sin(selRotation);
            selMode = SelectionMode.ROTATE;
            repaintSelectionRectangle();
            selLastX = x;
            selLastY = y;
        }
    }

    public Point transformSelPoint(Point p) {
        int x = transformSelX(p.x,p.y);
        int y = transformSelY(p.x,p.y);
        p.x = x;
        p.y = y;
        return p;
    }

    public void clickAction(MouseEvent e, Point pIn) {
        Point p = transformSelPoint(new Point(pIn));
        if(selMode == SelectionMode.NONE && e.getButton() == MouseEvent.BUTTON1) {
            selHalfWidth=selHalfHeight=0;
            selRotation = 0;
            cosSelRotation = 1;
            sinSelRotation = 0;
            setSelectionCenter(pIn.x, pIn.y);
        }
        else if(selMode == SelectionMode.WAIT && e.getButton()== MouseEvent.BUTTON1 && isInMoveArea(p)) {
            selLastX = pIn.x;
            selLastY = pIn.y;
            selMode = SelectionMode.MOVE;
        }
        else if(selMode == SelectionMode.WAIT && e.getButton()==MouseEvent.BUTTON1 && isInResizeArea(p)) {
            selMode = SelectionMode.RESIZE;
        } else if(selMode == SelectionMode.WAIT && e.getButton()==MouseEvent.BUTTON1 && isInRotateArea(pIn)) {
            selMode = SelectionMode.ROTATE;
        }
        else if(selMode == SelectionMode.WAIT)  {
            clearSelection(true);
        }

    }

    public boolean isSelectionValid() {
        return selHalfWidth>0 && selHalfHeight>0;
    }

    public boolean actionDone(MouseEvent e) {

        if(e.getButton()!=MouseEvent.BUTTON1 || !isSelectionValid()) {
            clearSelection(e.getClickCount()!=2);
        }
        else if(selMode != SelectionMode.NONE) {
            if (ptr.isAutoZoomToSelectionRectangleEnabled()) {
                return true;
            }
            else {
                selMode = SelectionMode.WAIT;
            }
        }
        return false;

    }

    public void clear() {
        clearSelection(true);
    }

    private Cursor getResizeCursor(int direction) {
        return Cursor.getPredefinedCursor(RESIZE_CURSORS[(direction+(int)Math.round(selRotation/Math.PI*4))&7]);
    }

    public Cursor getCursor(Point pIn) {
        if(pIn==null) return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);

        Point p = transformSelPoint(new Point(pIn));

        if(selMode == SelectionMode.WAIT || selMode == SelectionMode.RESIZE) {
            if(isInResizeAreaNW(p)) return getResizeCursor(1);
            else if(isInResizeAreaSW(p)) return getResizeCursor(3);
            else if(isInResizeAreaSE(p)) return getResizeCursor(5);
            else if(isInResizeAreaNE(p)) return getResizeCursor(7);
            else if(isInResizeAreaN(p)) return getResizeCursor(0);
            else if(isInResizeAreaW(p)) return getResizeCursor(2);
            else if(isInResizeAreaS(p)) return getResizeCursor(4);
            else if(isInResizeAreaE(p)) return getResizeCursor(6);
        }
        if((selMode == SelectionMode.WAIT || selMode == SelectionMode.MOVE) && isInMoveArea(p)) return Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
        else if((selMode == SelectionMode.WAIT && isInRotateArea(pIn)) || selMode == SelectionMode.ROTATE) return ROTATE_CURSOR;

        else return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

    public boolean canZoomToArea(Point p) {
        p = transformSelPoint(p);
        return selMode == SelectionMode.WAIT && (isInMoveArea(p) || isInResizeArea(p));
    }

    public boolean canZoom() {
        return selMode == SelectionMode.NONE;
    }
}
