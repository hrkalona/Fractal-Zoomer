
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class PaintOrbit extends Thread {
  protected Fractal pixel_orbit;
  protected BufferedImage image;
  protected ArrayList<Complex> complex_orbit;
  protected MainWindow ptr;
  protected boolean orbit_style;
  protected boolean julia;
  protected boolean grid;
  protected Color orbit_color;

    public PaintOrbit(double xCenter, double yCenter, double size, int max_iterations, int pixel_x, int pixel_y, BufferedImage image, MainWindow ptr, Color orbit_color, boolean orbit_style, boolean inverse_plane, boolean burning_ship, boolean grid, int function , double z_exponent) {

        double xPixel = xCenter - size / 2 + size * pixel_x / (ptr.getImageSize());
        double yPixel = yCenter - size / 2 + size * pixel_y / (ptr.getImageSize());

        complex_orbit = new ArrayList<Complex>(max_iterations + 1);
        complex_orbit.add(new Complex(xPixel, yPixel));

        switch (function) {
            case 0:
                pixel_orbit = new Mandelbrot(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship);
                break;
            case 1:
                pixel_orbit = new MandelbrotCubbed(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship);
                break;
            case 2:
                pixel_orbit = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship);
                break;
            case 3:
                pixel_orbit = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship);
                break;
            case 4:
                pixel_orbit = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship);
                break;
            case 5:
                pixel_orbit = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship);
                break;
            case 6:
                pixel_orbit = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship);
                break;
            case 7:
                pixel_orbit = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship);
                break;
            case 8:
                pixel_orbit = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship);
                break;
            case MainWindow.MANDELBROTNTH:
                pixel_orbit = new MandelbrotNth(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship, z_exponent);
                break;
            case MainWindow.LAMBDA:
                pixel_orbit = new Lambda(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);
                break;
            case MainWindow.MAGNET1:
                pixel_orbit = new Magnet1(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);
                break;
            case MainWindow.MAGNET2:
                pixel_orbit = new Magnet2(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);
                break;
            case MainWindow.NEWTON3:
                pixel_orbit = new Newton3(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);
                break;
            case MainWindow.NEWTON4:
                pixel_orbit = new Newton4(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);
                break;
            case MainWindow.NEWTONGENERALIZED3:
                pixel_orbit = new NewtonGeneralized3(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);
                break;
            case MainWindow.NEWTONGENERALIZED8:
                pixel_orbit = new NewtonGeneralized8(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);
                break;
            case MainWindow.BARNSLEY1:
                pixel_orbit = new Barnsley1(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);
                break;
            case MainWindow.BARNSLEY2:
                pixel_orbit = new Barnsley2(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);
                break;
            case MainWindow.BARNSLEY3:
                pixel_orbit = new Barnsley3(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);
                break;
            case MainWindow.MANDELBAR:
                pixel_orbit = new Mandelbar(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);
                break;
            case MainWindow.SPIDER:
                pixel_orbit = new Spider(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);
                break;
            case MainWindow.PHOENIX:
                pixel_orbit = new Phoenix(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);
                break;
        }


        this.image = image;
        this.ptr = ptr;
        this.orbit_style = orbit_style;
        this.orbit_color = orbit_color;
        this.grid = grid;
        julia = false;

    }

    public PaintOrbit(double xCenter, double yCenter, double size, int max_iterations, int pixel_x, int pixel_y, BufferedImage image, MainWindow ptr, Color orbit_color, boolean orbit_style, boolean inverse_plane, boolean burning_ship, boolean grid, int function , double z_exponent, double xJuliaCenter, double yJuliaCenter) {

        double xPixel = xCenter - size / 2 + size * pixel_x / (ptr.getImageSize());
        double yPixel = yCenter - size / 2 + size * pixel_y / (ptr.getImageSize());

        complex_orbit = new ArrayList<Complex>(max_iterations + 1);
        complex_orbit.add(new Complex(xPixel, yPixel));

        switch (function) {
            case 0:
                pixel_orbit = new Mandelbrot(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 1:
                pixel_orbit = new MandelbrotCubbed(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 2:
                pixel_orbit = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 3:
                pixel_orbit = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 4:
                pixel_orbit = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 5:
                pixel_orbit = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 6:
                pixel_orbit = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 7:
                pixel_orbit = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 8:
                pixel_orbit = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                pixel_orbit = new MandelbrotNth(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, burning_ship, z_exponent, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                pixel_orbit = new Lambda(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                pixel_orbit = new Magnet1(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                pixel_orbit = new Magnet2(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;           
            case MainWindow.BARNSLEY1:
                pixel_orbit = new Barnsley1(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                pixel_orbit = new Barnsley2(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                pixel_orbit = new Barnsley3(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                pixel_orbit = new Mandelbar(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                pixel_orbit = new Spider(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                pixel_orbit = new Phoenix(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
        }


        this.image = image;
        this.ptr = ptr;
        this.orbit_style = orbit_style;
        this.orbit_color = orbit_color;
        this.grid = grid;
        julia = true;

    }

    @Override
    public void run() {
        paint();
    }

    protected void paint() {

        if(grid) {
            ptr.drawGrid(image.createGraphics());
        }

        if(julia) {
            if(orbit_style) {
                drawJuliaLine();
            }
            else {
                drawJuliaDot();
            }
        }
        else {
            if(orbit_style) {
                drawFractalLine();
            }
            else {
                drawFractalDot();
            }
        }

        ((Graphics2D)ptr.getMainPanel().getGraphics()).drawImage(image, 0, 0, ptr);
              
    }

    private void drawFractalLine() {
      int x0, y0, x1 = 0, y1 = 0, list_size;

        Graphics2D full_image_g = image.createGraphics();

        pixel_orbit.calculateFractalOrbit();

        full_image_g.setColor(orbit_color);

        full_image_g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        list_size = complex_orbit.size() - 1;

        double size = pixel_orbit.getSize();
        int image_size = ptr.getImageSize();

        double temp_xcenter_size = pixel_orbit.getXCenter() - size / 2;
        double temp_ycenter_size = pixel_orbit.getYCenter() - size / 2;
        double temp_size_image_size = size / image_size;

        for(int i = 0; i < list_size; i++) {
            x0 = (int)((complex_orbit.get(i).getRe() - temp_xcenter_size) / temp_size_image_size);
            y0 = (int)((complex_orbit.get(i).getIm() - temp_ycenter_size) / temp_size_image_size);
            x1 = (int)((complex_orbit.get(i + 1).getRe() - temp_xcenter_size) / temp_size_image_size);
            y1 = (int)((complex_orbit.get(i + 1).getIm() - temp_ycenter_size) / temp_size_image_size);
            full_image_g.drawLine(x0, y0, x1, y1);
            full_image_g.fillOval(x0, y0, 3, 3);
        }
        
        full_image_g.fillOval(x1, y1, 3, 3);

    }

    private void drawJuliaLine() {
      int x0, y0, x1 = 0, y1 = 0, list_size;

        Graphics2D full_image_g = image.createGraphics();

        pixel_orbit.calculateJuliaOrbit();

        full_image_g.setColor(orbit_color);

        full_image_g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        list_size = complex_orbit.size() - 1;

        double size = pixel_orbit.getSize();
        int image_size = ptr.getImageSize();

        double temp_xcenter_size = pixel_orbit.getXCenter() - size / 2;
        double temp_ycenter_size = pixel_orbit.getYCenter() - size / 2;
        double temp_size_image_size = size / image_size;

        for(int i = 0; i < list_size; i++) {
            x0 = (int)((complex_orbit.get(i).getRe() - temp_xcenter_size) / temp_size_image_size);
            y0 = (int)((complex_orbit.get(i).getIm() - temp_ycenter_size) / temp_size_image_size);
            x1 = (int)((complex_orbit.get(i + 1).getRe() - temp_xcenter_size) / temp_size_image_size);
            y1 = (int)((complex_orbit.get(i + 1).getIm() - temp_ycenter_size) / temp_size_image_size);
            full_image_g.drawLine(x0, y0, x1, y1);
            full_image_g.fillOval(x0, y0, 3, 3);
        }
        
        full_image_g.fillOval(x1, y1, 3, 3);        

    }

    private void drawFractalDot() {
      int x0, y0, list_size;

        Graphics2D full_image_g = image.createGraphics();

        pixel_orbit.calculateFractalOrbit();

        full_image_g.setColor(orbit_color);

        full_image_g.setFont(new Font("Arial", 1 , 11));
        list_size = complex_orbit.size();

        double size = pixel_orbit.getSize();
        int image_size = ptr.getImageSize();

        double temp_xcenter_size = pixel_orbit.getXCenter() - size / 2;
        double temp_ycenter_size = pixel_orbit.getYCenter() - size / 2;
        double temp_size_image_size = size / image_size;

        for(int i = 0; i < list_size; i++) {
            x0 = (int)((complex_orbit.get(i).getRe() - temp_xcenter_size) / temp_size_image_size);
            y0 = (int)((complex_orbit.get(i).getIm() - temp_ycenter_size) / temp_size_image_size);
            full_image_g.drawString(".", x0 - 1, y0 + 1);
        }

    }

    private void drawJuliaDot() {
      int x0, y0, list_size;

        Graphics2D full_image_g = image.createGraphics();

        pixel_orbit.calculateJuliaOrbit();

        full_image_g.setColor(orbit_color);

        full_image_g.setFont(new Font("Arial", 1 , 11));
        list_size = complex_orbit.size();

        double size = pixel_orbit.getSize();
        int image_size = ptr.getImageSize();

        double temp_xcenter_size = pixel_orbit.getXCenter() - size / 2;
        double temp_ycenter_size = pixel_orbit.getYCenter() - size / 2;
        double temp_size_image_size = size / image_size;

        for(int i = 0; i < list_size; i++) {
            x0 = (int)((complex_orbit.get(i).getRe() - temp_xcenter_size) / temp_size_image_size);
            y0 = (int)((complex_orbit.get(i).getIm() - temp_ycenter_size) / temp_size_image_size);
            full_image_g.drawString(".", x0 - 1, y0 + 1);
        }

    }

}
