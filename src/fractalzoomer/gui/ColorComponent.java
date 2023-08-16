package fractalzoomer.gui;

import fractalzoomer.core.interpolation.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

public class ColorComponent extends JPanel {
    public static double NEON_PERCENTAGE = 0.25;
    private ArrayList<ColorPoint> colorPoints;
    private int selectedIndex;
    int width;
    int height;

    String name;
    private boolean wrapAround;

    Color color;
    Color secondColor;
    private ColorPaletteEditorPanel editor;
    private int circleSize = 8;

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

    public ColorComponent(Color color, Color secondColor, String name, int width, int height, int[][] data, ColorPaletteEditorPanel editor) {
        this.width = width;
        this.height = height;
        this.editor = editor;
        this.color = color;
        this.secondColor = secondColor;
        this.name = name;
        setPreferredSize(new Dimension(width, height));
        colorPoints = new ArrayList<>();

        if(data.length > 0) {
            if(data[0][0] != 0) {
                colorPoints.add(new ColorPoint(0, 0, true, true));
            }
        }
        else {
            colorPoints.add(new ColorPoint(0, 0, true, true));
        }

        for(int i = 0; i < data.length; i++) {
            colorPoints.add(new ColorPoint(data[i][0], (int)((data[i][1] / 255.0) * height + 0.5), i == 0 && data[i][0] == 0, true));
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
                    if(colorPoints.size() >= 1 && colorPoints.get(selectedIndex).canBeDeleted()) {
                        colorPoints.remove(selectedIndex);
                    }
                    selectedIndex = -1;
                }
                else if(e.getButton() == MouseEvent.BUTTON3 && selectedIndex == -1) {
                    if(editor.getAddOnAllComponents().isSelected()) {
                        editor.addOnAllComponents(x, y, height);
                    }
                    else {
                        addOnClick(x, y);
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
                    editor.colorChanged();
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

                repaint();
                
                if(closestIndex != -1) {
                    setToolTipText(name + ": " + (int)(((colorPoints.get(closestIndex).getY() / (double)height)) * 255 + 0.5));
                }
                else {
                    setToolTipText(name + ": " + (int)(((y / (double)height)) * 255 + 0.5));
                }


            }

        });

    }

    private void addOnClick(int x, int y) {
        if(((colorPoints.isEmpty())
                || (!colorPoints.isEmpty() && !colorPoints.get(0).isAnchor() && x < colorPoints.get(0).getX())
                || (colorPoints.size() == 1 && hasLastAnchor())) && x < 8) {
            colorPoints.add(new ColorPoint(0, y, true, true));
        }
        else {
            colorPoints.add(new ColorPoint(x, y, false, true));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setStroke(new BasicStroke(2));

        int maxX = ColorPaletteEditorPanel.getMaxX();

        Color lineColor;
        Color nodeColor;

        if(editor.getBackgroundMode() == 1) {
            GradientPaint gradient = new GradientPaint(0, 0, color, 0, height, Color.black);

            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, width, height);

            lineColor = Color.GRAY;
            nodeColor = Color.LIGHT_GRAY;
        }
        else {
            g.setColor(new Color(240, 240, 240));
            g.fillRect(0, 0, width, height);

            lineColor = secondColor;
            nodeColor = color;
        }

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        g.setColor(lineColor);

        if(editor.getInterpolationMode() == 0 && editor.getInterpolationColorMode() == 0) {

            if(!colorPoints.isEmpty()) {
                if(colorPoints.get(0).getX() != 0) {
                    g.drawLine(0, height - colorPoints.get(0).getY(), colorPoints.get(0).getX(), height - colorPoints.get(0).getY());
                }

                if(colorPoints.get(colorPoints.size() - 1).getX() != maxX) {
                    g.drawLine(colorPoints.get(colorPoints.size() - 1).getX(), height - colorPoints.get(colorPoints.size() - 1).getY(), maxX, height - colorPoints.get(colorPoints.size() - 1).getY());
                }
            }

            for (int i = 0; i < colorPoints.size() - 1; i++) {
                g.drawLine(colorPoints.get(i).getX(), height - colorPoints.get(i).getY(), colorPoints.get(i + 1).getX(), height - colorPoints.get(i + 1).getY());
            }
        }
        else {
            g2d.setStroke(new BasicStroke(1.5f));

            if(!colorPoints.isEmpty()) {
                if(colorPoints.get(0).getX() != 0) {
                    int x = 0;
                    int xp1 = colorPoints.get(0).getX();

                    for(int curx = x; curx < xp1; curx++) {
                        int val1 = getValue(curx, false, 0, maxX);
                        int val2 = getValue(curx + 1, false, 0, maxX);
                        g.drawLine(curx, height - (int)((val1 / 255.0) * height + 0.5), curx + 1, height - (int)((val2 / 255.0) * height + 0.5));
                    }
                }

                if(colorPoints.get(colorPoints.size() - 1).getX() != maxX) {
                    int x = colorPoints.get(colorPoints.size() - 1).getX();
                    int xp1 = maxX;

                    for(int curx = x; curx < xp1; curx++) {
                        int val1 = getValue(curx, false, 0, maxX);
                        int val2 = getValue(curx + 1, false, 0, maxX);
                        g.drawLine(curx, height - (int)((val1 / 255.0) * height + 0.5), curx + 1, height - (int)((val2 / 255.0) * height + 0.5));
                    }
                }
            }

            for (int i = 0; i < colorPoints.size() - 1; i++) {
                int x = colorPoints.get(i).getX();
                int xp1 = colorPoints.get(i + 1).getX();

                for(int curx = x; curx < xp1; curx++) {
                    int val1 = getValue(curx, false, 0, maxX);
                    int val2 = getValue(curx + 1, false, 0, maxX);
                    g.drawLine(curx, height - (int)((val1 / 255.0) * height + 0.5), curx + 1, height - (int)((val2 / 255.0) * height + 0.5));
                }
            }
            g2d.setStroke(new BasicStroke(2));
        }

        if (colorPoints.size() > 1 && wrapAround) {
            float[] dash1 = {5.0f};
            BasicStroke dashed
                    = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f, dash1, 0.0f);
            g2d.setStroke(dashed);
            g.drawLine(maxX, height - colorPoints.get(colorPoints.size() - 1).getY(), 0, height - colorPoints.get(0).getY());
        }

        // Draw color points

        g2d.setStroke(new BasicStroke(2));
        for (ColorPoint colorPoint : colorPoints) {

            if(colorPoint.isAnchor()) {
                g.setColor(Color.BLACK);
            }
            else {
                g.setColor(nodeColor);
            }
            g.fillOval(colorPoint.getX() - circleSize / 2, (height - colorPoint.getY()) - circleSize / 2, circleSize, circleSize);
            g.setColor(lineColor);
            g.drawOval(colorPoint.getX() - circleSize / 2, (height - colorPoint.getY()) - circleSize / 2, circleSize, circleSize);
        }

        //Filler
        if(!colorPoints.isEmpty()) {
            if(colorPoints.get(0).getX() != 0) {
                g.setColor(Color.ORANGE);
                g.fillOval(- circleSize / 2, (height - colorPoints.get(0).getY()) - circleSize / 2, circleSize, circleSize);
                g.setColor(lineColor);
                g.drawOval(- circleSize / 2, (height - colorPoints.get(0).getY()) - circleSize / 2, circleSize, circleSize);
            }

            if(colorPoints.get(colorPoints.size() - 1).getX() != maxX) {
                g.setColor(Color.ORANGE);
                g.fillOval(maxX - circleSize / 2, (height - colorPoints.get(colorPoints.size() - 1).getY()) - circleSize / 2, circleSize, circleSize);
                g.setColor(lineColor);
                g.drawOval(maxX - circleSize / 2, (height - colorPoints.get(colorPoints.size() - 1).getY()) - circleSize / 2, circleSize, circleSize);
            }
        }

    }

    private double getCoef(double coef) {

        switch (editor.getInterpolationMode()) {
            case 0:
                return LinearInterpolation.getCoefficient(coef);
            case 1:
                return CosineInterpolation.getCoefficient(coef);
            case 2:
                return AccelerationInterpolation.getCoefficient(coef);
            case 3:
                return DecelerationInterpolation.getCoefficient(coef);
            case 4:
                return ExponentialInterpolation.getCoefficient(coef);
            case 5:
                return CatmullRomInterpolation.getCoefficient(coef);
            case 6:
                return CatmullRom2Interpolation.getCoefficient(coef);
            case 7:
                return SigmoidInterpolation.getCoefficient(coef);
            case 8:
                return SineInterpolation.getCoefficient(coef);
            case 9:
                return SqrtInterpolation.getCoefficient(coef);
            case 10:
                return ThirdPolynomialInterpolation.getCoefficient(coef);
            case 11:
                return FifthPolynomialInterpolation.getCoefficient(coef);
            case 12:
                return Exponential2Interpolation.getCoefficient(coef);
            case 13:
                return CbrtInterpolation.getCoefficient(coef);
            case 14:
                return FrthrootInterpolation.getCoefficient(coef);
            case 15:
                return SmoothTransitionFunctionInterpolation.getCoefficient(coef);
            case 16:
                return QuarterSinInterpolation.getCoefficient(coef);
            default:
                return coef;

        }
    }

    private int getCustomColorComponent() {

        if(name.equals("R")) {
            return editor.getIntermediateColor().getRed();
        }
        else if(name.equals("G")) {
            return editor.getIntermediateColor().getGreen();
        }
        else {
            return editor.getIntermediateColor().getBlue();
        }
    }

    public int getValue(double x, boolean wrapAround, int stepWrap, int maxX) {
        for(int i = 0; i < colorPoints.size() - 1; i++) {

            if (x >= colorPoints.get(i).getX() && x < colorPoints.get(i + 1).getX()) {
                double yc = colorPoints.get(i).getY();
                double y1 = colorPoints.get(i + 1).getY();
                double xc = colorPoints.get(i).getX();
                double x1 = colorPoints.get(i + 1).getX();
                return getFinalColor(x, xc, yc, x1, y1);
            }

        }

        if(colorPoints.isEmpty()) {
            return 0;
        }

        if(x < colorPoints.get(0).getX()) {

            ColorPoint cp = colorPoints.get(0);
            double yc = cp.getY();
            double y1 = yc;
            double xc = 0;
            double x1 = cp.getX();

            if(x1 == xc) {
                return (int) ((yc / (double) height) * 255 + 0.5);
            }

            return getFinalColor(x, xc, yc, x1, y1);
        }

        if(wrapAround && x >= maxX) {
            int index = colorPoints.size() - 1;
            double lastX = maxX + stepWrap;

            if(x <= lastX) {
                double yc = colorPoints.get(index).getY();
                double y1 = colorPoints.get(0).getY();
                double xc = maxX;
                double x1 = lastX;

                return getFinalColor(x, xc, yc, x1, y1);
            }
            else {
                return (int) ((colorPoints.get(0).getY() / (double) height) * 255 + 0.5);
            }
        }
        else {
            ColorPoint cp = colorPoints.get(colorPoints.size() - 1);
            double yc = cp.getY();
            double y1 = yc;
            double xc = cp.getX();
            double x1 = maxX;

            if(x1 == xc) {
                return (int) ((yc / (double) height) * 255 + 0.5);
            }

            return getFinalColor(x, xc, yc, x1, y1);
        }
    }

    private int getFinalColor(double x, double xc, double yc, double x1, double y1) {

        if(editor.getInterpolationColorMode() == 1 || editor.getInterpolationColorMode() == 4 || editor.getInterpolationColorMode() == 7) {
            if(xc != x1) {
                x1--;
            }
            if(editor.getInterpolationColorMode() == 1) {
                y1 = 0;
            }
            else if(editor.getInterpolationColorMode() == 4) {
                y1 = height;
            }
            else {
                y1 = (getCustomColorComponent() / 255.0) * height;
            }
        }
        else if(editor.getInterpolationColorMode() == 2 || editor.getInterpolationColorMode() == 5 || editor.getInterpolationColorMode() == 8) {
            if(editor.getInterpolationColorMode() == 2) {
                yc = 0;
            }
            else if(editor.getInterpolationColorMode() == 5) {
                yc = height;
            }
            else {
                yc = (getCustomColorComponent() / 255.0) * height;
            }
        }
        else if(editor.getInterpolationColorMode() == 3 || editor.getInterpolationColorMode() == 6 || editor.getInterpolationColorMode() == 9) {
            if(xc != x1) {
                x1--;
            }
            if(x < xc + (x1 - xc) * NEON_PERCENTAGE ) {
                y1 = yc;
                if(editor.getInterpolationColorMode() == 3) {
                    yc = 0;
                }
                else if(editor.getInterpolationColorMode() == 6) {
                    yc = height;
                }
                else {
                    yc = (getCustomColorComponent() / 255.0) * height;
                }
                x1 = xc + (x1 - xc) * NEON_PERCENTAGE;
            }
            else if(x >= xc + (x1 - xc) * NEON_PERCENTAGE && x < xc + (x1 - xc) * (1 - NEON_PERCENTAGE)) {
                double temp = xc + (x1 - xc) * NEON_PERCENTAGE;
                x1 = xc + (x1 - xc) * (1 - NEON_PERCENTAGE);
                xc = temp;
            }
            else {
                yc = y1;

                if(editor.getInterpolationColorMode() == 3) {
                    y1 = 0;
                }
                else if(editor.getInterpolationColorMode() == 6) {
                    y1 = height;
                }
                else {
                    y1 = (getCustomColorComponent() / 255.0) * height;
                }
                xc = xc + (x1 - xc) * (1 - NEON_PERCENTAGE);
            }
        }

        if (editor.getInterpolationColorMode() == 10) {
            double newY = yc +  (1 - getCoef((x - xc) / (x1 - xc))) * (y1 - yc);
            return (int) ((newY / (double) height) * 255 + 0.5);
        }
        else if (editor.getInterpolationColorMode() < 10) {
            double newY = yc + getCoef((x - xc) / (x1 - xc)) * (y1 - yc);
            return (int) ((newY / (double) height) * 255 + 0.5);
        }
        else {
            double newY = yc + fractional_transfer(getCoef((x - xc) / (x1 - xc)), editor.getInterpolationColorMode()) * (y1 - yc);
            return (int) ((newY / (double) height) * 255 + 0.5);
        }


    }

    private double fractional_transfer(double fract_part, int fractional_transfer) {

        switch (fractional_transfer) {
            case 11:
                double temp = 2*fract_part-1;
                fract_part = 1 - temp * temp;
                break;
            case 12:
                temp = 2*fract_part-1;
                fract_part = temp * temp;
                break;
            case 13:
                temp = 2*fract_part-1;
                temp *= temp;
                fract_part = 1 - temp * temp;
                break;
            case 14:
                temp = 2*fract_part-1;
                temp *= temp;
                fract_part = temp * temp;
                break;
            case 15:
                fract_part = Math.sin(fract_part * Math.PI);
                break;
            case 16:
                fract_part = 1 - Math.sin(fract_part * Math.PI);
                break;
            case 17:
                if(fract_part < 0.5) {
                    fract_part = 2 * fract_part;
                }
                else {
                    fract_part = 2 - 2 *fract_part;
                }
                break;
            case 18:
                if(fract_part < 0.5) {
                    fract_part = 1 - 2 * fract_part;
                }
                else {
                    fract_part = 1 - (2 - 2 *fract_part);
                }
                break;
            case 19:
                fract_part = 0.5 - 0.5 * Math.cos(2 * fract_part * Math.PI);
                break;
            case 20:
                fract_part = 0.5 + 0.5 * Math.cos(2 * fract_part * Math.PI);
                break;
            case 21:
                if(fract_part < 0.5) {
                    fract_part = Math.sqrt(2 * fract_part);
                }
                else {
                    fract_part = Math.sqrt(2 - 2 *fract_part);
                }
                break;
            case 22:
                if(fract_part < 0.5) {
                    fract_part = 1 - Math.sqrt(2 * fract_part);
                }
                else {
                    fract_part = 1 - Math.sqrt(2 - 2 *fract_part);
                }
                break;
        }

        return fract_part;
    }

    public int getMaxX() {
        if(colorPoints.isEmpty()) {
            return -1;
        }
        return colorPoints.get(colorPoints.size() - 1).getX();
    }

    public int getLength() {
        return colorPoints.size();
    }

    public void setWrapAround(boolean wrapAround) {
        this.wrapAround = wrapAround;
    }

    public void addAnchorAtTheEnd(boolean val) {
        if(val && !hasLastAnchor()) {
            colorPoints.add(new ColorPoint(width, 0, true, false));
        }
        else {
            if(hasLastAnchor()) {
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
                colorPoints.add(new ColorPoint(0, 0, true, true));
            }
        }
        else {
            colorPoints.add(new ColorPoint(0, 0, true, true));
        }

        for(int i = 0; i < data.length; i++) {
            colorPoints.add(new ColorPoint(data[i][0], (int)((data[i][1] / 255.0) * height + 0.5), i == 0 && data[i][0] == 0, true));
        }

        Collections.sort(colorPoints);
        repaint();
    }

    public boolean hasLastAnchor() {
        return colorPoints.size() >= 1 && colorPoints.get(colorPoints.size() - 1).getX() != 0 && colorPoints.get(colorPoints.size() - 1).isAnchor() && !colorPoints.get(colorPoints.size() - 1).canBeDeleted();
    }

    public void clear() {
        colorPoints.clear();
    }

    public void eraseAll() {
        colorPoints.clear();
    }

    public void add(int x, int y) {
        colorPoints.add(new ColorPoint(x, (int) ((y / 255.0) * height + 0.5), false, true));
    }

    public void addWithFirstAnchor(int x, int y) {
        colorPoints.add(new ColorPoint(x, (int)((y / 255.0) * height + 0.5), x == 0, true));
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
