package fractalzoomer.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

public class ColorComponent extends JPanel {
    private ArrayList<ColorPoint> colorPoints;
    private int selectedIndex;
    int width;
    int height;
    private boolean wrapAround;

    Color color;
    private ColorPaletteEditorPanel editor;
    private int circleSize = 8;
    private int mode;

    public void setEditor(ColorPaletteEditorPanel editor) {
        this.editor = editor;
    }

    public ArrayList<ColorPoint> getColorPoints() {
        return colorPoints;
    }

    public void setColorPoints(ArrayList<ColorPoint> colorPoints) {
        this.colorPoints = colorPoints;

        Collections.sort(this.colorPoints);
    }

    public ColorComponent(Color color, String name, int width, int height, int[][] data, ColorPaletteEditorPanel editor) {
        this.width = width;
        this.height = height;
        this.editor = editor;
        this.color = color;
        setPreferredSize(new Dimension(width, height));
        colorPoints = new ArrayList<>();

        if(data.length > 0) {
            if(data[0][0] != 0) {
                colorPoints.add(new ColorPoint(0, 0, true));
            }
        }
        else {
            colorPoints.add(new ColorPoint(0, 0, true));
        }

        if(color.equals(Color.RED)) {
            mode = 0;
        }
        else if(color.equals(Color.GREEN)) {
            mode = 1;
        }
        else {
            mode = 2;
        }

        for(int i = 0; i < data.length; i++) {
            colorPoints.add(new ColorPoint(data[i][0], (int)((data[i][1] / 255.0) * height + 0.5), i == 0 && data[i][0] == 0));
        }

        Collections.sort(colorPoints);


        selectedIndex = -1;


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                int x = e.getX();
                int y = height - e.getY();
                int closestIndex = -1;
                double closestDistance = circleSize / 2.0 + 2;
                for (int i = 0; i < colorPoints.size(); i++) {
                    double tempX = x - colorPoints.get(i).getX();
                    double tempY = y - colorPoints.get(i).getY();
                    double distance = Math.sqrt(tempX * tempX + tempY * tempY);
                    if (distance < closestDistance) {
                        closestIndex = i;
                        closestDistance = distance;
                    }
                }
                selectedIndex = closestIndex;

                if(e.getButton() == MouseEvent.BUTTON3 && selectedIndex != -1) {
                    if(colorPoints.size() >= 2 && !colorPoints.get(selectedIndex).isAnchor()) {
                        colorPoints.remove(selectedIndex);
                    }
                    selectedIndex = -1;
                }
                else if(e.getButton() == MouseEvent.BUTTON3 && selectedIndex == -1) {
                    if(editor.getAddOnAllComponents().isSelected()) {
                        editor.addOnAllComponents(x, y, height);
                    }
                    else {
                        colorPoints.add(new ColorPoint(x, y, false));
                    }
                }

                Collections.sort(colorPoints);

                repaint();
                editor.paintPalette();

            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = height - e.getY();
                if (selectedIndex >= 0 && x >= 0 && x <= getWidth() && y >= 0 && y <= getHeight()) {

                    if(editor.getLockPoints().isSelected()) {
                        if(selectedIndex > 0 && selectedIndex < colorPoints.size() - 1) {
                            if(x > colorPoints.get(selectedIndex - 1).getX() &&
                                    x < colorPoints.get(selectedIndex + 1).getX()) {
                                colorPoints.get(selectedIndex).setX(x);
                            }
                        }
                        else {
                            colorPoints.get(selectedIndex).setX(x);
                        }
                    }
                    else {
                        colorPoints.get(selectedIndex).setX(x);
                    }
                    colorPoints.get(selectedIndex).setY(y);
                    Collections.sort(colorPoints);
                    repaint();
                    editor.paintPalette();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedIndex = -1;
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = height - e.getY();
                int closestIndex = -1;
                double closestDistance = circleSize / 2.0 + 2;
                for (int i = 0; i < colorPoints.size(); i++) {
                    double tempX = x - colorPoints.get(i).getX();
                    double tempY = y - colorPoints.get(i).getY();
                    double distance = Math.sqrt(tempX * tempX + tempY * tempY);
                    if (distance < closestDistance) {
                        closestIndex = i;
                        closestDistance = distance;
                    }
                }

                if(closestIndex != -1) {
                    setToolTipText(name + ": " + (int)(((colorPoints.get(closestIndex).getY() / (double)height)) * 255 + 0.5));
                }
                else {
                    setToolTipText(null);
                }
            }

        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, width, height);

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(2));

        if(mode == 0) {
            g.setColor(new Color(110, 70, 70));
        }
        else if(mode == 1) {
            g.setColor(new Color(70, 110, 70));
        }
        else if(mode == 2) {
            g.setColor(new Color(70, 70, 110));
        }

        for (int i = 0; i < colorPoints.size() - 1; i++) {
            g.drawLine(colorPoints.get(i).getX(), height - colorPoints.get(i).getY(), colorPoints.get(i + 1).getX(), height - colorPoints.get(i + 1).getY());
        }

        if(colorPoints.size() > 1 && wrapAround) {
            float[] dash1 = {5.0f};
            BasicStroke dashed
                    = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f, dash1, 0.0f);
            g2d.setStroke(dashed);
            g.drawLine(colorPoints.get(colorPoints.size() - 1).getX(), height - colorPoints.get(colorPoints.size() - 1).getY(), colorPoints.get(0).getX(), height - colorPoints.get(0).getY());
        }

        // Draw color points
        for (ColorPoint colorPoint : colorPoints) {
            g.setColor(color);
            g.fillOval(colorPoint.getX()  - circleSize / 2, (height - colorPoint.getY()) - circleSize / 2, circleSize, circleSize);
        }

    }

    public int getValue(double x, boolean wrapAround, int stepWrap) {
        for(int i = 0; i < colorPoints.size() - 1; i++) {
            if(x >= colorPoints.get(i).getX() && x < colorPoints.get(i + 1).getX()) {
                double newY = colorPoints.get(i).getY() + (x - (double)colorPoints.get(i).getX()) * ((((double)colorPoints.get(i + 1).getY() - (double)colorPoints.get(i).getY())) / ((double)colorPoints.get(i + 1).getX() - (double)colorPoints.get(i).getX()));
                return (int)((newY / (double)height) * 255 + 0.5);
            }
        }

        if(wrapAround) {
            int index = colorPoints.size() - 1;
            double lastX = colorPoints.get(index).getX() + stepWrap;

            if(x <= lastX) {
                double newY = colorPoints.get(index).getY() + (x - (double) colorPoints.get(index).getX()) * ((((double) colorPoints.get(0).getY() - (double) colorPoints.get(index).getY())) / (lastX - (double) colorPoints.get(index).getX()));
                return (int) ((newY / (double) height) * 255 + 0.5);
            }
            else {
                return (int) ((colorPoints.get(0).getY() / (double) height) * 255 + 0.5);
            }
        }
        else {
            return (int) ((colorPoints.get(colorPoints.size() - 1).getY() / (double) height) * 255 + 0.5);
        }
    }

    public int getMaxX() {
        return colorPoints.get(colorPoints.size() - 1).getX();
    }

    public void setWrapAround(boolean wrapAround) {
        this.wrapAround = wrapAround;
    }

    public void addAnchorAtTheEnd(boolean val) {
        if(val) {
            colorPoints.add(new ColorPoint(width, 0, true));
        }
        else {
            if(colorPoints.size() >= 2 && colorPoints.get(colorPoints.size() - 1).isAnchor()) {
                colorPoints.remove(colorPoints.get(colorPoints.size() - 1));
            }
        }

        Collections.sort(colorPoints);
        repaint();
        editor.paintPalette();
    }

    public void setData(int[][] data) {
        colorPoints.clear();

        if(data.length > 0) {
            if(data[0][0] != 0) {
                colorPoints.add(new ColorPoint(0, 0, true));
            }
        }
        else {
            colorPoints.add(new ColorPoint(0, 0, true));
        }

        for(int i = 0; i < data.length; i++) {
            colorPoints.add(new ColorPoint(data[i][0], (int)((data[i][1] / 255.0) * height + 0.5), i == 0 && data[i][0] == 0));
        }

        Collections.sort(colorPoints);
        repaint();
    }

    public boolean hasLastAnchor() {
        return colorPoints.size() >= 2 && colorPoints.get(colorPoints.size() - 1).isAnchor();
    }

    public void clear() {
        colorPoints.clear();
        colorPoints.add(new ColorPoint(0, 0, true));
    }

    public void eraseAll() {
        colorPoints.clear();
    }

    public void add(int x, int y) {
        colorPoints.add(new ColorPoint(x, (int)((y / 255.0) * height + 0.5), false));
    }

    public void addWithFirstAnchor(int x, int y) {
        colorPoints.add(new ColorPoint(x, (int)((y / 255.0) * height + 0.5), x == 0));
    }

    public void update() {
        Collections.sort(colorPoints);
        repaint();
        editor.paintPalette();
    }

    public int getAverageStep() {
        double average_step = 0;
        int samples = 0;
        for(int i = 0; i < colorPoints.size() - 1; i++) {
            samples++;
            average_step += colorPoints.get(i + 1).getX() - colorPoints.get(i).getX();
        }

        if(samples == 0) {
            return 1;
        }
        else {
            return (int)(average_step / samples + 0.5);
        }
    }
}
