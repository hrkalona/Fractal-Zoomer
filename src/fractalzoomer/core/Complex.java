package fractalzoomer.core;





/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class Complex {
  public static final double pi_2 = Math.PI / 2;
  private double re;
  private double im;
  
    public Complex() {
        
        re = 0;
        im = 0;
        
    }

    public Complex(double re, double im) {

        this.re = re;
        this.im = im;

    }
    
    public Complex(Complex z) {
        
        re = z.re;
        im = z.im;
        
    }

    public double getRe() {

        return re;

    }

    public double getIm() {

        return im;

    }

    /*
     * z1 + z2
     */
    public Complex plus(Complex z) {

        return new Complex(re + z.re, im + z.im);

    }
    
    /*
     *  z + Real
     */
    public Complex plus(double number) {
        
        return new Complex(re + number, im);
        
    }
    
    /*
     *  z + Imaginary
     */
    public Complex plus_i(double number) {
        
        return new Complex(re, im + number);
        
    }

    /*
     *  z1 - z2
     */
    public Complex sub(Complex z) {

        return new Complex(re - z.re, im - z.im);

    }
    
    /*
     *  z - Real
     */
    public Complex sub(double number) {
        
        return new Complex(re - number, im);
        
    }
    
     /*
     *  Real - z1
     */
    public Complex r_sub(double number) {
        
        return new Complex(number - re, -im);
        
    }
    
    /*
     *  z - Imaginary
     */
    public Complex sub_i(double number) {
        
        return new Complex(re, im - number);
        
    }
    
    /*
     *  Imaginary - z 
     */
    public Complex i_sub(double number) {
        
        return new Complex(-re, number - im);
        
    }
    
  

    /*
     *  z1 * z2
     */
    public Complex times(Complex z) {

       double  temp = z.re;
       double  temp2 = z.im;

       return new Complex(re * temp - im * temp2, re * temp2 + im * temp);
        
        //Gauss
        /*double temp1 = z.re;
        double temp2 = z.im;
        
        double k1 = temp1 * (re + im);
        double k2 = re * (temp2 - temp1);
        double k3 = im * (temp1 + temp2);
        
        return new Complex(k1 - k3, k1 + k2); */
       
    }
    
    /*
     *  z1 * Real
     */
    public Complex times(double number) {
        
        return new Complex(re * number, im * number);
        
    }
    
    /*
     *  z * Imaginary
     */
    public Complex times_i(double number) {
        
        return new Complex(-im * number, re * number);
                
    }
   
    /*
     *  z1 / z2
     */
    public Complex divide(Complex z) {

        double  temp = z.re;
        double  temp2 = z.im;
        double  temp3 = temp * temp + temp2 * temp2;
           
        return new Complex((re * temp + im * temp2) / temp3, (im * temp - re * temp2) / temp3);

    }
    
    /*
     *  z1 / Real
     */
    public Complex divide(double number) {
        
        return new Complex(re / number, im / number);
        
    }
    
    /*
     *  Real / z1
     */
    public Complex r_divide(double number) {
              
        double temp = number / (re * re + im * im);
        
        return new Complex(re * temp, (-im) * temp);
 
    }
    
    /*
     *  Imaginary / z1
     */
    public Complex i_divide(double number) {

        double temp = number / (re * re + im * im);
        
        return new Complex(im * temp, re * temp);
        
    }
    
     /*
      *  1 / z
      */
     public Complex reciprocal() {
         
        double temp = re * re + im * im;
        
        return new Complex(re / temp, (-im) / temp);
        
     }

    /*
     *  z^2
     */
    public Complex square() {

        double temp = re * im;
        
        return new Complex((re + im) * (re - im), temp + temp);
        
    }

     /*
     *  z^3
     */
    public Complex cube() {

        double temp = re * re;
        double temp2 = im * im;

        return new Complex(re * (temp - 3 * temp2), im * (3 * temp - temp2));
        
    }

     /*
     *  z^4
     */
    public Complex fourth() {

        double temp = re * re;
        double temp2 = im * im;

        return new Complex(temp * (temp - 6 * temp2) + temp2 * temp2, 4 * re * im * (temp - temp2));

    }

     /*
     *  z^5
     */
    public Complex fifth() {

        double temp = re * re;
        double temp2 = im * im;

        return new Complex(re * (temp * temp + temp2 * (5 * temp2 - 10 * temp)), im * (temp2 * temp2 + temp * (5 * temp - 10 * temp2)));

    }

     /*
     *  z^6
     */
    public Complex sixth() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp2 * temp2;

        return new Complex(temp * (temp * temp + 15 * temp2 * (temp2 - temp)) - temp3 * temp2, re * im * (temp * (6 * temp - 20 * temp2) + 6 * temp3));

    }

     /*
     *  z^7
     */
    public Complex seventh() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp2 * temp2;

        return new Complex(re * (temp * temp * temp + temp2 * (temp * (35 * temp2 - 21 * temp) - 7 * temp3)), im * (temp * (temp * (7 * temp - 35 * temp2) + 21 * temp3) - temp3 * temp2));

    }

     /*
     *  z^8
     */
    public Complex eighth() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp * temp;
        double temp4 = temp2 * temp2;

        return new Complex(temp * (temp3 * temp + 28 * temp2 * (temp * (2.5 * temp2 - temp) - temp4)) + temp4 * temp4, 8 * re * im * (temp * (7 * temp2 * (temp2 - temp) + temp3) - temp4 * temp2));

    }

     /*
     *  z^9
     */
    public Complex ninth() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp * temp;
        double temp4 = temp2 * temp2;

        return new Complex(re * (im * (im * (re * (re * (im * (im * (126 * temp - 84 * temp2)) - 36 * temp3)) + 9 * temp4 * temp2)) + temp3 * temp3), im * (re * (re * (im * (im * (re * (re * (126 * temp2 - 84 * temp)) - 36 * temp4)) + 9 * temp3 * temp)) + temp4 * temp4));

    }

     /*
     *  z^10
     */
    public Complex tenth() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp * temp;
        double temp4 = temp2 * temp2;
        double temp5 = temp3 * temp;
        double temp6 = temp4 * temp2;

        return new Complex(temp * (temp5 * temp + temp2 * (temp * (210 * temp2 * (temp - temp2) - 45 * temp3) + 45 * temp6)) - temp6 * temp4, 10 * re * im * (temp * (12 * temp2 * (temp * (2.1 * temp2 - temp) - temp4) + temp5) + temp6 * temp2));

    }
   
     /*
     *  |z|^2
     */
    public double norm_squared() {

        return re * re + im * im;
       
    }
    
     /*
     *  |z|
     */
    public double norm() {

        return Math.sqrt(re * re + im * im);
       
    }
    
    /*
     *  |z1 - z2|^2
     */
    public double distance_squared(Complex z) {
        
        double temp_re = re - z.re;
        double temp_im = im - z.im;
        
        return temp_re * temp_re + temp_im * temp_im;
        
    }
    
    /*
     *  |z1 - Real|^2
     */
    public double distance_squared(double number) {
        
        double temp_re = re - number;
        
        return temp_re * temp_re + im * im;
        
    }
    
     /*
     *  |z1 - z2|
     */
    public double distance(Complex z) {
        
        double temp_re = re - z.re;
        double temp_im = im - z.im;
        
        return Math.sqrt(temp_re * temp_re + temp_im * temp_im);
        
    }
    
     /*
     *  |z1 - Real|
     */
    public double distance(double number) {
        
        double temp_re = re - number;
        
        return Math.sqrt(temp_re * temp_re + im * im);
        
    }

     /*
     *  |Real|
     */
    public double absRe() {

        return re >= 0 ? re : -re;

    }

     /*
     *  |Imaginary|
     */
    public double absIm() {

        return im >= 0 ? im : -im;

    }
    
    /*
     *  abs(z)
     */
    public Complex abs() {
        
        return new Complex(re >= 0 ? re : -re, im >= 0 ? im : -im);
        
    }

     /*
     *  z = Real -Imaginary i
     */
    public Complex conjugate() {

        return new Complex(re, -im);
        
    }
    
    /*
     *  -z
     */
     public Complex negative() {
         
         return new Complex(-re, -im);
         
     }

     /*
     *  z^n
     */
    public Complex pow(double exponent) {

	double temp = Math.pow(re * re + im * im, exponent * 0.5);
	double temp2 = exponent * Math.atan2(im, re);

        return new Complex(temp * Math.cos(temp2), temp * Math.sin(temp2));

    }
    
     /*
      *  z1 ^ z2 = exp(z2 * log(z1))
      */
     public Complex pow(Complex z) { 
       
        return (z.times(this.log())).exp();
         
     }
    
    /*
     *  log(z) = ln|z| + arctan(Im/Re)i
     */
    public Complex log() {
        
        return new Complex(Math.log(re * re + im * im) * 0.5, Math.atan2(im, re));
        
    }
    
    /*
     *  cos(z) = (exp(iz) + exp(-iz)) / 2
     */
    public Complex cos() {
        
        double temp = Math.exp(-im);
        
        double cos_re = Math.cos(re);
        double sin_re = Math.sin(re);
        
        Complex temp2 = new Complex(temp * cos_re, temp * sin_re);

        double temp3 = Math.exp(im);
        Complex temp4 = new Complex(temp3 * cos_re, temp3 * -sin_re);
        
        return (temp2.plus(temp4)).divide(2);
        
    }
    
    /*
     *  cosh(z) = (exp(z) + exp(-z)) / 2
     */
     public Complex cosh() {
        
        double temp = Math.exp(re);
        
        double cos_im = Math.cos(im);
        double sin_im = Math.sin(im);
        
        Complex temp2 = new Complex(temp * cos_im, temp * sin_im);

        double temp3 = Math.exp(-re);
        Complex temp4 = new Complex(temp3 * cos_im, temp3 * -sin_im);
        
        return (temp2.plus(temp4)).divide(2);
        
    }
     
     /*
      *  acos(z) = pi / 2 + ilog(iz + sqrt(1 - z^2))
      */
     public Complex acos() {
        
         return this.asin().r_sub(pi_2);
         
     }
     
     /*
      *  acosh(z) = log(z + sqrt(z^2 - 1))
      */
     public Complex acosh() {
        
         return this.plus((this.square().sub(1)).sqrt()).log();
         
     }
    
    /*
     *  sin(z) = (exp(iz) - exp(-iz)) / 2i
     */
    public Complex sin() {
        
        double temp = Math.exp(-im);
        
        double cos_re = Math.cos(re);
        double sin_re = Math.sin(re);
        
        Complex temp2 = new Complex(temp * cos_re, temp * sin_re);

        double temp3 = Math.exp(im);
        Complex temp4 = new Complex(temp3 * cos_re, temp3 * -sin_re);
        
        return (temp2.sub(temp4)).divide(new Complex(0, 2));
        
    }

    /*
     *  sinh(z) = (exp(z) - exp(-z)) / 2
     */
    public Complex sinh() {
        
        double temp = Math.exp(re);
        
        double cos_im = Math.cos(im);
        double sin_im = Math.sin(im);
        
        Complex temp2 = new Complex(temp * cos_im, temp * sin_im);

        double temp3 = Math.exp(-re);
        Complex temp4 = new Complex(temp3 * cos_im, temp3 * -sin_im);
        
        return (temp2.sub(temp4)).divide(2);
        
    }
    
     /*
      *  asin(z) =-ilog(iz + sqrt(1 - z^2))
      */
     public Complex asin() {
        
         return this.times_i(1).plus((this.square().r_sub(1)).sqrt()).log().times_i(-1);
         
     }
     
     /*
      *  asinh(z) = log(z + sqrt(z^2 + 1))
      */
     public Complex asinh() {
        
         return this.plus((this.square().plus(1)).sqrt()).log();
         
     }
        
    /*
     *  tan(z) = (1 - exp(-2zi)) / i(1 + exp(-2zi))
     */
    public Complex tan() {
        
        double temp = Math.exp(2 * im);
        
        double temp3 = 2 * re;
        
        double cos_re = Math.cos(temp3);
        double sin_re = Math.sin(temp3);
        
        Complex temp2 = new Complex(temp * cos_re, temp * -sin_re);
        
        return (temp2.r_sub(1)).divide((temp2.plus(1)).times_i(1));
        
    }
    
    /*
     *  tahn(z) = (1 - exp(-2z)) / (1 + exp(-2z))
     */
    public Complex tanh() {
    
        double temp = Math.exp(-2 * re);
        
        double temp3 = 2 * im;
        
        double cos_im = Math.cos(temp3);
        double sin_im = Math.sin(temp3);
        
        Complex temp2 = new Complex(temp * cos_im, temp * -sin_im);
        
        return (temp2.r_sub(1)).divide(temp2.plus(1));
      
    }
    
     /*
      *  atan(z) = (i / 2)log((1 - iz) / (iz + 1))
      */
     public Complex atan() {
        
         Complex temp = this.times_i(1);
         
         return ((temp.r_sub(1)).divide(temp.plus(1))).log().times_i(0.5);
         
     }
     
     /*
      *  atanh(z) = (1 / 2)log((z + 1) / (1 - z))
      */
     public Complex atanh() {
   
         return ((this.plus(1)).divide(this.r_sub(1))).log().times(0.5);
         
     }
    
    /*
     *  cot(z) = i(1 + exp(-2zi)) / (1 - exp(-2zi))
     */
    public Complex cot() {

        double temp = Math.exp(2 * im);
        
        double temp3 = 2 * re;
        
        double cos_re = Math.cos(temp3);
        double sin_re = Math.sin(temp3);
        
        Complex temp2 = new Complex(temp * cos_re, temp * -sin_re);
        
        return (temp2.times_i(1).plus_i(1)).divide(temp2.r_sub(1));
        
    }
    
    /*
     *  coth(z) =  (1 + exp(-2z)) / (1 - exp(-2z))
     */
    public Complex coth() {
        
        double temp = Math.exp(-2 * re);
        
        double temp3 = 2 * im;
        
        double cos_im = Math.cos(temp3);
        double sin_im = Math.sin(temp3);
        
        Complex temp2 = new Complex(temp * cos_im, temp * -sin_im);
        
        return (temp2.plus(1)).divide(temp2.r_sub(1));
  
    }
    
     /*
      *  acot(z) = (i / 2)log((z^2 - iz) / (z^2 + iz))
      */
     public Complex acot() {
        
         Complex temp = this.times_i(1);
         Complex temp2 = this.square();
         
         return ((temp2.sub(temp)).divide(temp2.plus(temp))).log().times_i(0.5);
         
     }
     
     /*
      *  acoth(z) = (1 / 2)log((1 + 1/z) / (1 - 1/z))
      */
     public Complex acoth() {
        
         Complex temp = this.reciprocal();
         
         return ((temp.plus(1)).divide(temp.r_sub(1))).log().times(0.5);
         
     }
     
     /*
      *  sec(z) = 1 / cos(z)
      */
     public Complex sec() {

         return this.cos().reciprocal();
         
     }
     
     /*
      *  asec(z) = pi / 2 + ilog(sqrt(1 - 1 / z^2) + i / z)
      */
     public Complex asec() {

         return (((this.square().reciprocal()).r_sub(1).sqrt()).plus(this.i_divide(1))).log().times_i(1).plus(pi_2);
         
     }
     
     /*
      *  sech(z) = 1 / cosh(z)
      */
     public Complex sech() {

         return this.cosh().reciprocal();
         
     }
     
     /*
      *  asech(z) = log(sqrt(1 / z^2 - 1) + 1 / z)
      */
     public Complex asech() {

         return (((this.square().reciprocal()).sub(1).sqrt()).plus(this.reciprocal())).log();
         
     }
     
     /*
      *  csc(z) = 1 / sin(z)
      */
     public Complex csc() {

         return this.sin().reciprocal();
         
     }
     
     /*
      *  acsc(z) = -ilog(sqrt(1 - 1 / z^2) + i / z)
      */
     public Complex acsc() {

         return (((this.square().reciprocal()).r_sub(1).sqrt()).plus(this.i_divide(1))).log().times_i(-1);
         
     }
     
     /*
      *  csch(z) = 1 / sinh(z)
      */
     public Complex csch() {

         return this.sinh().reciprocal();
         
     }
     
     /*
      *  acsch(z) = log(sqrt(1 / z^2 + 1) + 1 / z)
      */
     public Complex acsch() {

         return (((this.square().reciprocal()).plus(1).sqrt()).plus(this.reciprocal())).log();
         
     }
 
     /*
      *  exp(z) = exp(Re(z)) * (cos(Im(z)) + sin(Im(z))i)
      */
     public Complex exp() {
         
        double temp = Math.exp(re);

        return new Complex(temp * Math.cos(im), temp * Math.sin(im)); 

     }
     
     
     /*
      * sqrt(z) = z^0.5
      */
     public Complex sqrt() {
         
        double temp = Math.pow(re * re + im * im, 0.25);
	double temp2 = 0.5 * Math.atan2(im, re);

        return new Complex(temp * Math.cos(temp2), temp * Math.sin(temp2));
        
     }
     
     /*
     *  sin, (sin)'
     */
     public Complex[] der01_sin() {
        
        double temp = Math.exp(-im);
        
        double cos_re = Math.cos(re);
        double sin_re = Math.sin(re);
        
        Complex temp2 = new Complex(temp * cos_re, temp * sin_re);

        double temp3 = Math.exp(im);
        Complex temp4 = new Complex(temp3 * cos_re, temp3 * -sin_re);
        
        Complex[] sin_and_der = new Complex[2];
        
        sin_and_der[0] = (temp2.sub(temp4)).divide(new Complex(0, 2));
        sin_and_der[1] = (temp2.plus(temp4)).divide(2);
                
        return sin_and_der;
        
    }
    
    /*
     *  sin, (sin)', (sin)''
     */
     public Complex[] der012_sin() {
        
        double temp = Math.exp(-im);
        
        double cos_re = Math.cos(re);
        double sin_re = Math.sin(re);
        
        Complex temp2 = new Complex(temp * cos_re, temp * sin_re);

        double temp3 = Math.exp(im);
        Complex temp4 = new Complex(temp3 * cos_re, temp3 * -sin_re);
        
        Complex[] sin_and_der = new Complex[3];
        
        sin_and_der[0] = (temp2.sub(temp4)).divide(new Complex(0, 2));
        sin_and_der[1] = (temp2.plus(temp4)).divide(2);
        sin_and_der[2] = sin_and_der[0].negative();
                
        return sin_and_der;
        
    }
     
     
     /*
     *  cos, (cos)', (cos)''
     */
     public Complex[] der01_cos() {
        
        double temp = Math.exp(-im);
        
        double cos_re = Math.cos(re);
        double sin_re = Math.sin(re);
        
        Complex temp2 = new Complex(temp * cos_re, temp * sin_re);

        double temp3 = Math.exp(im);
        Complex temp4 = new Complex(temp3 * cos_re, temp3 * -sin_re);
        
        Complex[] sin_and_der = new Complex[2];
        
        sin_and_der[0] = (temp2.plus(temp4)).divide(2);
        sin_and_der[1] = (temp4.sub(temp2)).divide(new Complex(0, 2));

        
        return sin_and_der;
        
    }
     
     /*
     *  cos, (cos)', (cos)''
     */
     public Complex[] der012_cos() {
        
        double temp = Math.exp(-im);
        
        double cos_re = Math.cos(re);
        double sin_re = Math.sin(re);
        
        Complex temp2 = new Complex(temp * cos_re, temp * sin_re);

        double temp3 = Math.exp(im);
        Complex temp4 = new Complex(temp3 * cos_re, temp3 * -sin_re);
        
        Complex[] sin_and_der = new Complex[3];
        
        sin_and_der[0] = (temp2.plus(temp4)).divide(2);
        sin_and_der[1] = (temp4.sub(temp2)).divide(new Complex(0, 2));
        sin_and_der[2] = sin_and_der[0].negative();
        
        return sin_and_der;
        
    }
     
     /*
      *  The closest Gaussian Integer to the Complex number
      */
     public Complex gaussian_integer() {
         
         return new Complex((int)(re < 0 ? re - 0.5 : re + 0.5), (int)(im < 0 ? im - 0.5 : im + 0.5));
         
     }
     
     @Override
     public String toString() {
         
         String temp = "";
                
         if(im > 0) {
             temp = re + "+" + im + "i";
         }
         else if(im == 0) {
             temp = re + "+" + (0.0) + "i";
         }
         else {
             temp = re + "" + im + "i";
         }

         return temp;
         
     }
     
     
     public Complex fold_out(double number) {
         
         double norm = re * re + im * im;

         return norm > number ? this.divide(norm) : this;
         
     }
     
     public Complex fold_in(double number) {
         
         double norm = re * re + im * im;
         
         return norm < number ? this.divide(norm) : this;
         
     } 

     public Complex fold_right(double number) {

         return re < number ? new Complex(re + 2 * (number - re), im) : this;
         
     }
     
     public Complex fold_left(double number) {

         return re > number ? new Complex(re - 2 * (re - number), im) : this;
         
     }
     
     public Complex fold_up(double number) {

         return im < number ? new Complex(re, im + 2 * (number - im)) : this;
         
     }
     
     public Complex fold_down(double number) {

         return im > number ? new Complex(re, im - 2 * (im - number)) : this;
         
     }
 
     
}
    

