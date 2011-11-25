/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class Complex {
  private double re;
  private double im;

    public Complex(double re, double im) {

        this.re = re;
        this.im = im;

    }

    public double getRe() {

        return re;

    }

    public double getIm() {

        return im;

    }

    public Complex plus(Complex z) {

        return new Complex(re + z.getRe(), im + z.getIm());

    }
    
    public Complex plusNormal(double number) {
        
        return new Complex(re + number, im);
        
    }

    public Complex sub(Complex z) {

        return new Complex(re - z.getRe(), im - z.getIm());

    }
    
    public Complex subNormal(double number) {
        
        return new Complex(re - number, im);
        
    }
    
    public Complex subNormalInv(double number) {
        
        return new Complex(number - re, -im);
        
    }

    public Complex times(Complex z) {

        double  temp = z.getRe();
        double  temp2 = z.getIm();

        return new Complex(re * temp - im * temp2, re * temp2 + im * temp);
       
    }
    
    public Complex timesNormal(double number) {
        
        return new Complex(re * number, im * number);
        
    }
   
    public Complex divide(Complex z) {

        double  temp = z.getRe();
        double  temp2 = z.getIm();
        double  temp3 = temp * temp + temp2 * temp2;
           
        return new Complex((re * temp + im * temp2) / temp3, (im * temp - re * temp2) / temp3);

    }
    
    public Complex divideNormal(double number) {
        
        return new Complex(re / number, im / number);
        
    }
    
    public Complex divideNormalInv(double number) {
              
        double temp = number / (re * re + im * im);
        
        return new Complex(re * temp, (-im) * temp);
        
    }

    public Complex square() {

        return new Complex(re * re - im * im, 2 * re * im);
        
    }

    public Complex cube() {

        double temp = re * re;
        double temp2 = im * im;

        return new Complex(re * (temp - 3 * temp2), im * (3 * temp - temp2));
        
    }

    public Complex fourth() {

        double temp = re * re;
        double temp2 = im * im;

        return new Complex(temp * (temp - 6 * temp2) + temp2 * temp2, 4 * re * im * (temp - temp2));

    }

    public Complex fifth() {

        double temp = re * re;
        double temp2 = im * im;

        return new Complex(re * (temp * temp + temp2 * (5 * temp2 - 10 * temp)), im * (temp2 * temp2 + temp * (5 * temp - 10 * temp2)));

    }

    public Complex sixth() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp2 * temp2;

        return new Complex(temp * (temp * temp + 15 * temp2 * (temp2 - temp)) - temp3 * temp2, re * im * (temp * (6 * temp - 20 * temp2) + 6 * temp3));

    }

    public Complex seventh() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp2 * temp2;

        return new Complex(re * (temp * temp * temp + temp2 * (temp * (35 * temp2 - 21 * temp) - 7 * temp3)), im * (temp * (temp * (7 * temp - 35 * temp2) + 21 * temp3) - temp3 * temp2));

    }

    public Complex eighth() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp * temp;
        double temp4 = temp2 * temp2;

        return new Complex(temp * (temp3 * temp + 28 * temp2 * (temp * (2.5 * temp2 - temp) - temp4)) + temp4 * temp4, 8 * re * im * (temp * (7 * temp2 * (temp2 - temp) + temp3) - temp4 * temp2));

    }

    public Complex ninth() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp * temp;
        double temp4 = temp2 * temp2;

        return new Complex(re * (im * (im * (re * (re * (im * (im * (126 * temp - 84 * temp2)) - 36 * temp3)) + 9 * temp4 * temp2)) + temp3 * temp3), im * (re * (re * (im * (im * (re * (re * (126 * temp2 - 84 * temp)) - 36 * temp4)) + 9 * temp3 * temp)) + temp4 * temp4));

    }

    public Complex tenth() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp * temp;
        double temp4 = temp2 * temp2;
        double temp5 = temp3 * temp;
        double temp6 = temp4 * temp2;

        return new Complex(temp * (temp5 * temp + temp2 * (temp * (210 * temp2 * (temp - temp2) - 45 * temp3) + 45 * temp6)) - temp6 * temp4, 10 * re * im * (temp * (12 * temp2 * (temp * (2.1 * temp2 - temp) - temp4) + temp5) + temp6 * temp2));

    }
   
    public double magnitude() {

        return re * re + im * im; //Math.sqrt(Math.pow(re, 2) + Math.pow(im, 2)); //for faster calculation. So for mandelbrot we checking if magnitude > 4 which is 2 squared, since we are not using the root in this function
       
    }

    public double absRe() {

        return re >= 0 ? re : -re;

    }

    public double absIm() {

        return im >= 0 ? im : -im;

    }

    public Complex conjugate() {

        return new Complex(re, -im);
        
    }

    public Complex pow(double exponent) {
        
        double temp = Math.pow(re * re + im * im, exponent / 2);
        double temp2 = exponent * Math.atan(im / re);

	//double temp = Math.pow(re * re + im * im, exponent / 2);
	//double temp2 = exponent * Math.atan2(im, re);// * -1.0;

        return new Complex(temp * Math.cos(temp2), temp * Math.sin(temp2));

    }
    
}
