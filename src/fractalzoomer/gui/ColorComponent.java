package fractalzoomer.gui;

import fractalzoomer.core.interpolation.*;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;

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

        sort();


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
                    if(!colorPoints.isEmpty() && colorPoints.get(selectedIndex).canBeDeleted()) {
                        if(editor.getLinkedPoints()) {
                            editor.removeFromAll(colorPoints.get(selectedIndex).getX(), selectedIndex);
                        }
                        else {
                            colorPoints.remove(selectedIndex);
                        }
                    }
                    selectedIndex = -1;
                }
                else if(e.getButton() == MouseEvent.BUTTON3 && selectedIndex == -1) {
                    if(editor.getAddOnAllComponents() || editor.getLinkedPoints()) {
                        editor.addOnAllComponents(x, y, height);
                    }
                    else {
                        addOnClick(x, y);
                    }
                }

                sort();

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

                    boolean moved = false;
                    int old_x = -1;
                    if(editor.getLockPoints()) {
                        if(selectedIndex > 0 && selectedIndex < colorPoints.size() - 1) {
                            if(x > colorPoints.get(selectedIndex - 1).getX() &&
                                    x < colorPoints.get(selectedIndex + 1).getX()) {
                                old_x = colorPoints.get(selectedIndex).getX();
                                moved = colorPoints.get(selectedIndex).setX(x);
                            }
                        }
                        else {
                            old_x = colorPoints.get(selectedIndex).getX();
                            moved = colorPoints.get(selectedIndex).setX(x);
                        }
                    }
                    else {
                        old_x = colorPoints.get(selectedIndex).getX();
                        moved = colorPoints.get(selectedIndex).setX(x);
                    }

                    colorPoints.get(selectedIndex).setY(y);

                    if(editor.getLinkedPoints() && moved) {
                        editor.moveHorizontalAll(x, old_x, selectedIndex);
                    }

                    if (moved) {
                        sort();
                    }

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
                    ColorPoint cp = colorPoints.get(closestIndex);
                    setToolTipText(name + ": " + (int)(((cp.getY() / (double)height)) * 255 + 0.5));
                    if(cp.isAnchor()) {
                        setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
                    }
                    else {
                        setCursor(new Cursor(Cursor.MOVE_CURSOR));
                    }
                }
                else {
                    setToolTipText(name + ": " + (int)(((y / (double)height)) * 255 + 0.5));
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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

        if(MainWindow.useCustomLaf) {
            g.setColor(Color.LIGHT_GRAY);
        }
        else {
            g.setColor(Color.BLACK);
        }
        g2d.setStroke(new BasicStroke(1));
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        g2d.setStroke(new BasicStroke(2));
        g.setColor(lineColor);

        if(editor.getInterpolationMode() == 0 && editor.getInterpolationColorMode() == 0 && (!editor.getLinkedPoints() || editor.getColorSpace() == 0)) {

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

            int[] palette = editor.getPalette(true);

            for(int i = 0; i < maxX; i++) {
                int v1, v2;
                if(name.equals("R")) {
                    v1 = (palette[i] >> 16) & 0xFF;
                    v2 = i < maxX - 1 ? (palette[i + 1] >> 16) & 0xFF : (int)((colorPoints.get(colorPoints.size() - 1).getY() / (double)height) * 255 + 0.5);
                }
                else if(name.equals("G")) {
                    v1 = (palette[i] >> 8) & 0xFF;
                    v2 = i < maxX - 1 ? (palette[i + 1] >> 8) & 0xFF : (int)((colorPoints.get(colorPoints.size() - 1).getY() / (double)height) * 255 + 0.5);
                }
                else {
                    v1 = palette[i] & 0xFF;
                    v2 = i < maxX - 1 ? palette[i + 1] & 0xFF : (int)((colorPoints.get(colorPoints.size() - 1).getY() / (double)height) * 255 + 0.5);
                }
                g.drawLine(i, height - (int) ((v1 / 255.0) * height + 0.5), i + 1, height - (int) ((v2 / 255.0) * height + 0.5));
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

            if( colorPoints.get(colorPoints.size() - 1).getX() != maxX) {
                g.setColor(Color.ORANGE);
                g.fillOval(maxX - circleSize / 2, (height - colorPoints.get(colorPoints.size() - 1).getY()) - circleSize / 2, circleSize, circleSize);
                g.setColor(lineColor);
                g.drawOval(maxX - circleSize / 2, (height - colorPoints.get(colorPoints.size() - 1).getY()) - circleSize / 2, circleSize, circleSize);
            }
        }

    }

    private static double getCoef(double coef, int interpolation_method) {
        return InterpolationMethod.create(interpolationMethodMapping(interpolation_method)).getCoef(coef);
    }

    public static int interpolationMethodMapping(int interpolation_method) {
        switch (interpolation_method) {
            case 0:
                return Constants.INTERPOLATION_LINEAR;
            case 1:
                return Constants.INTERPOLATION_COSINE;
            case 2:
                return Constants.INTERPOLATION_ACCELERATION;
            case 3:
                return Constants.INTERPOLATION_DECELERATION;
            case 4:
                return Constants.INTERPOLATION_EXPONENTIAL;
            case 5:
                return Constants.INTERPOLATION_CATMULLROM;
            case 6:
                return Constants.INTERPOLATION_CATMULLROM2;
            case 7:
                return Constants.INTERPOLATION_SIGMOID;
            case 8:
                return Constants.INTERPOLATION_SINE;
            case 9:
                return Constants.INTERPOLATION_SQRT;
            case 10:
                return Constants.INTERPOLATION_THIRD_POLY;
            case 11:
                return Constants.INTERPOLATION_FIFTH_POLY;
            case 12:
                return Constants.INTERPOLATION_EXPONENTIAL_2;
            case 13:
                return Constants.INTERPOLATION_CUBE_ROOT;
            case 14:
                return Constants.INTERPOLATION_FOURTH_ROOT;
            case 15:
                return Constants.INTERPOLATION_SMOOTH_TRANSITION_STEP;
            case 16:
                return Constants.INTERPOLATION_QUARTER_SIN;
            default:
                return Constants.INTERPOLATION_LINEAR;

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

    public static int getValueLinked(double x, boolean wrapAround, int stepWrap, int maxX, ColorComponent red, ColorComponent green, ColorComponent blue, int height, int interpolation_method, int interpolation_color_mode, int custom_red, int custom_green, int custom_blue, int color_space) {
        ArrayList<ColorPoint> reds = red.colorPoints;
        ArrayList<ColorPoint> greens = green.colorPoints;
        ArrayList<ColorPoint> blues = blue.colorPoints;

        for(int i = 0; i < reds.size() - 1; i++) {

            if (x >= reds.get(i).getX() && x < reds.get(i + 1).getX()) {
                double ycr = reds.get(i).getY();
                double y1r = reds.get(i + 1).getY();
                double ycg = greens.get(i).getY();
                double y1g = greens.get(i + 1).getY();
                double ycb = blues.get(i).getY();
                double y1b = blues.get(i + 1).getY();
                double xc = reds.get(i).getX();
                double x1 = reds.get(i + 1).getX();

                return getFinalColorLinked(x, xc, x1, ycr, y1r, ycg, y1g, ycb, y1b, interpolation_method, interpolation_color_mode, color_space, height, custom_red, custom_green, custom_blue);
            }

        }

        if(reds.isEmpty()) {
            return 0;
        }

        if(x < reds.get(0).getX()) {

            ColorPoint cpr = reds.get(0);
            ColorPoint cpg = greens.get(0);
            ColorPoint cpb = blues.get(0);

            double ycr = cpr.getY();
            double y1r = ycr;

            double ycg = cpg.getY();
            double y1g = ycg;

            double ycb = cpb.getY();
            double y1b = ycb;
            double xc = 0;
            double x1 = cpr.getX();

            if(x1 == xc) {
                return 0xff000000 | ((int) ((ycr / height) * 255 + 0.5) << 16) | ((int) ((ycg / height) * 255 + 0.5) << 8) | ((int) ((ycb / height) * 255 + 0.5));
            }

            return getFinalColorLinked(x, xc, x1, ycr, y1r, ycg, y1g, ycb, y1b, interpolation_method, interpolation_color_mode, color_space, height, custom_red, custom_green, custom_blue);
        }

        if(wrapAround && x >= maxX) {
            int index = reds.size() - 1;
            double lastX = maxX + stepWrap;

            if(x <= lastX) {
                double ycr = reds.get(index).getY();
                double y1r = reds.get(0).getY();
                double ycg = greens.get(index).getY();
                double y1g = greens.get(0).getY();
                double ycb = blues.get(index).getY();
                double y1b = blues.get(0).getY();
                double xc = maxX;
                double x1 = lastX;

                return getFinalColorLinked(x, xc, x1, ycr, y1r, ycg, y1g, ycb, y1b, interpolation_method, interpolation_color_mode, color_space, height, custom_red, custom_green, custom_blue);
            }
            else {
                return 0xff000000 | ((int) ((reds.get(0).getY() / (double) height) * 255 + 0.5) << 16) | ((int) ((greens.get(0).getY() / (double) height) * 255 + 0.5) << 8) | ((int) ((blues.get(0).getY() / (double) height) * 255 + 0.5));
            }
        }
        else {
            ColorPoint cpr = reds.get(reds.size() - 1);
            ColorPoint cpg = greens.get(greens.size() - 1);
            ColorPoint cpb = blues.get(blues.size() - 1);
            double ycr = cpr.getY();
            double y1r = ycr;
            double ycg = cpg.getY();
            double y1g = ycg;
            double ycb = cpb.getY();
            double y1b = ycb;
            double xc = cpr.getX();
            double x1 = maxX;

            if(x1 == xc) {
                return 0xff000000 | ((int) ((ycr / height) * 255 + 0.5) << 16) | ((int) ((ycg / height) * 255 + 0.5) << 8) | ((int) ((ycb / height) * 255 + 0.5));
            }

            return getFinalColorLinked(x, xc, x1, ycr, y1r, ycg, y1g, ycb, y1b, interpolation_method, interpolation_color_mode, color_space, height, custom_red, custom_green, custom_blue);
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
                return (int) ((yc / height) * 255 + 0.5);
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
                return (int) ((yc / height) * 255 + 0.5);
            }

            return getFinalColor(x, xc, yc, x1, y1);
        }
    }

    private static double[] transformValues(double x, double xc, double yc, double x1, double y1, int interpolation_color_mode, int height, int custom_color_component) {
        if(interpolation_color_mode == 1 || interpolation_color_mode == 4 || interpolation_color_mode == 7) {
            if(xc != x1) {
                x1--;
            }
            if(interpolation_color_mode == 1) {
                y1 = 0;
            }
            else if(interpolation_color_mode == 4) {
                y1 = height;
            }
            else {
                y1 = (custom_color_component / 255.0) * height;
            }
        }
        else if(interpolation_color_mode == 2 || interpolation_color_mode == 5 || interpolation_color_mode == 8) {
            if(interpolation_color_mode == 2) {
                yc = 0;
            }
            else if(interpolation_color_mode == 5) {
                yc = height;
            }
            else {
                yc = (custom_color_component / 255.0) * height;
            }
        }
        else if(interpolation_color_mode == 3 || interpolation_color_mode == 6 || interpolation_color_mode == 9) {
            if(xc != x1) {
                x1--;
            }
            if(x < xc + (x1 - xc) * NEON_PERCENTAGE ) {
                y1 = yc;
                if(interpolation_color_mode == 3) {
                    yc = 0;
                }
                else if(interpolation_color_mode == 6) {
                    yc = height;
                }
                else {
                    yc = (custom_color_component / 255.0) * height;
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

                if(interpolation_color_mode == 3) {
                    y1 = 0;
                }
                else if(interpolation_color_mode == 6) {
                    y1 = height;
                }
                else {
                    y1 = (custom_color_component / 255.0) * height;
                }
                xc = xc + (x1 - xc) * (1 - NEON_PERCENTAGE);
            }
        }

        return new double[] {xc, yc, x1, y1};
    }

    private int getFinalColor(double x, double xc, double yc, double x1, double y1) {

        double[] res = transformValues(x, xc, yc, x1, y1, editor.getInterpolationColorMode(), height, getCustomColorComponent());
        xc = res[0];
        yc = res[1];
        x1 = res[2];
        y1 = res[3];

        double coef = getFinalCoef(editor.getInterpolationColorMode(), editor.getInterpolationMode(), x, xc, x1, true);

        double newY = yc + coef * (y1 - yc);
        return (int) ((newY / height) * 255 + 0.5);

    }

    private static int getFinalColorLinked(double x, double xc, double x1, double ycr, double y1r, double ycg, double y1g, double ycb, double y1b, int interpolation_method, int interpolation_color_mode, int color_space, int height, int custom_red, int custom_green, int custom_blue) {

        double[] res_red = transformValues(x, xc, ycr, x1, y1r, interpolation_color_mode, height, custom_red);
        double[] res_green = transformValues(x, xc, ycg, x1, y1g, interpolation_color_mode, height, custom_green);
        double[] res_blue = transformValues(x, xc, ycb, x1, y1b, interpolation_color_mode, height, custom_blue);

        xc = res_red[0];
        x1 = res_red[2];

        ycr = res_red[1];
        y1r = res_red[3];
        ycg = res_green[1];
        y1g = res_green[3];
        ycb = res_blue[1];
        y1b = res_blue[3];
        double coef = getFinalCoef(interpolation_color_mode, interpolation_method, x, xc, x1, false);

        InterpolationMethod method = InterpolationMethod.create(interpolationMethodMapping(interpolation_method));

        int r1 = (int)((ycr / height) * 255 + 0.5);
        int g1 = (int)((ycg / height) * 255 + 0.5);
        int b1 = (int)((ycb / height) * 255 + 0.5);

        int r2 = (int)((y1r / height) * 255 + 0.5);
        int g2 = (int)((y1g / height) * 255 + 0.5);
        int b2 = (int)((y1b / height) * 255 + 0.5);

        return InterpolationMethod.interpolateColors(color_space, method, coef, 0, r1, g1, b1, r2, g2, b2);
    }

    private static double getFinalCoef(int interpolation_color_mode, int interpolation_method, double x, double xc, double x1, boolean applyCoefFuction) {
        if(applyCoefFuction) {
            if (interpolation_color_mode == 10) {
                return 1 - getCoef((x - xc) / (x1 - xc), interpolation_method);
            } else if (interpolation_color_mode < 10) {
                return getCoef((x - xc) / (x1 - xc), interpolation_method);
            } else {
                return getCoef(fractional_transfer((x - xc) / (x1 - xc), interpolation_color_mode), interpolation_method);
            }
        }
        else {
            if (interpolation_color_mode == 10) {
                return 1 - (x - xc) / (x1 - xc);
            } else if (interpolation_color_mode < 10) {
                return (x - xc) / (x1 - xc);
            } else {
                return fractional_transfer((x - xc) / (x1 - xc), interpolation_color_mode);
            }
        }
    }

    private static double fractional_transfer(double fract_part, int fractional_transfer) {

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

        sort();
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

        sort();
        repaint();
    }

    public boolean hasLastAnchor() {
        return !colorPoints.isEmpty() && colorPoints.get(colorPoints.size() - 1).getX() != 0 && colorPoints.get(colorPoints.size() - 1).isAnchor() && !colorPoints.get(colorPoints.size() - 1).canBeDeleted();
    }

    public void clear() {
        colorPoints.clear();
    }

    public void eraseAll() {
        colorPoints.clear();
    }

    public void addColor(int x, int color_val) {
        if(color_val < 0) {
            addOnClick(x, color_val);
        }
        else {
            addOnClick(x, (int) ((color_val / 255.0) * height + 0.5));
        }
    }

    public void addWithFirstAnchor(int x, int y) {
        colorPoints.add(new ColorPoint(x, (int)((y / 255.0) * height + 0.5), x == 0, true));
    }

    public void sort() {
        Collections.sort(colorPoints);
    }

    public void update() {
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

    public boolean hasPoint(int x) {
        for(ColorPoint p : colorPoints) {
            if(p.getX() == x) {
                return true;
            }
        }
        return false;
    }

    public void fixMissing() {
        sort();
        for(int i = 0; i < colorPoints.size(); i++) {
            if(colorPoints.get(i).getY() < 0) {
                if(i == 0) {
                    if(i + 1 < colorPoints.size()) {
                        colorPoints.get(i).setY(colorPoints.get(i + 1).getY());
                    }
                    else {
                        colorPoints.get(i).setY(0);
                    }
                }
                else if(i > 0 && i < colorPoints.size() - 1) {
                    double xp = colorPoints.get(i - 1).getX();
                    double x1 = colorPoints.get(i + 1).getX();
                    double yp = colorPoints.get(i - 1).getY();
                    double y1 = colorPoints.get(i + 1).getY();
                    double newY = yp + ((colorPoints.get(i).getX() - xp) / (x1 - xp)) * (y1 - yp);
                    colorPoints.get(i).setY((int) (newY + 0.5));
                }
                else if(i > 0) {
                    colorPoints.get(i).setY(colorPoints.get(i - 1).getY());
                }
            }
        }
        repaint();
    }
}
