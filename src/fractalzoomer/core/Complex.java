package fractalzoomer.core;





/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public final class Complex {
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

    public final double getRe() {

        return re;

    }

    public final double getIm() {

        return im;

    }
    
    public final void assign(Complex z) {
        
        re = z.re;
        im = z.im;
        
    }

    /*
     * z1 + z2
     */
    public final Complex plus(Complex z) {

        return new Complex(re + z.re, im + z.im);

    }
    
    /*
     * z1 = z1 + z2
     */
    public final Complex plus_mutable(Complex z) {

        re = re + z.re;
        im = im + z.im;
        
        return this;

    }
    
    /*
     *  z + Real
     */
    public final Complex plus(double number) {
        
        return new Complex(re + number, im);
        
    }
    
    /*
     *  z = z + Real
     */
    public final Complex plus_mutable(double number) {
        
        re = re + number;
        
        return this;
        
    }
    
    /*
     *  z + Imaginary
     */
    public final Complex plus_i(double number) {
        
        return new Complex(re, im + number);
        
    }
    
    /*
     *  z = z + Imaginary
     */
    public final Complex plus_i_mutable(double number) {
        
        im = im + number;
        
        return this;
        
    }

    /*
     *  z1 - z2
     */
    public final Complex sub(Complex z) {

        return new Complex(re - z.re, im - z.im);

    }
    
    /*
     *  z1 = z1 - z2
     */
    public final Complex sub_mutable(Complex z) {

        re = re - z.re;
        im = im - z.im;
        
        return this;

    }
    
    /*
     *  z - Real
     */
    public final Complex sub(double number) {
        
        return new Complex(re - number, im);
        
    }
    
    /*
     *  z = z - Real
     */
    public final Complex sub_mutable(double number) {
        
        re = re - number;
        
        return this;
        
    }
    
     /*
     *  Real - z1
     */
    public final Complex r_sub(double number) {
        
        return new Complex(number - re, -im);
        
    }
    
    /*
     *  z1 = Real - z1
     */
    public final Complex r_sub_mutable(double number) {
        
        re = number - re;
        im = -im;
        
        return this;
        
    }
    
    /*
     *  z - Imaginary
     */
    public final Complex sub_i(double number) {
        
        return new Complex(re, im - number);
        
    }
    
    /*
     *  z = z - Imaginary
     */
    public final Complex sub_i_mutable(double number) {
        
        im = im - number;
        
        return this;
        
    }
    
    /*
     *  Imaginary - z 
     */
    public final Complex i_sub(double number) {
        
        return new Complex(-re, number - im);
        
    }
    
    /*
     *  z = Imaginary - z 
     */
    public final Complex i_sub_mutable(double number) {
        
        re = -re;
        im = number - im;
        
        return this;
        
    }
    

    /*
     *  z1 * z2
     */
    public final Complex times(Complex z) {

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
     *  z1 = z1 * z2
     */
    public final Complex times_mutable(Complex z) {

       double  temp = z.re;
       double  temp2 = z.im;

       double temp3 = re * temp - im * temp2;
       im = re * temp2 + im * temp;
       re = temp3;
       
       return this;
       
    }
    
    /*
     *  z1 * Real
     */
    public final Complex times(double number) {
        
        return new Complex(re * number, im * number);
        
    }
    
    /*
     *  z1 = z1 * Real
     */
    public final Complex times_mutable(double number) {
        
        re = re * number;
        im = im * number;
        
        return this;
        
    }
    
    /*
     *  z * Imaginary
     */
    public final Complex times_i(double number) {
        
        return new Complex(-im * number, re * number);
                
    }
    
    /*
     *  z = z * Imaginary
     */
    public final Complex times_i_mutable(double number) {
        
        double temp = -im * number;
        im = re * number;
        re = temp;
        
        return this;
                
    }
   
    /*
     *  z1 / z2
     */
    public final Complex divide(Complex z) {

        double  temp = z.re;
        double  temp2 = z.im;
        double  temp3 = temp * temp + temp2 * temp2;
           
        return new Complex((re * temp + im * temp2) / temp3, (im * temp - re * temp2) / temp3);

    }
    
    /*
     *  z1 = z1 / z2
     */
    public final Complex divide_mutable(Complex z) {

        double  temp = z.re;
        double  temp2 = z.im;
        double  temp3 = temp * temp + temp2 * temp2;
           
        double temp4 = (re * temp + im * temp2) / temp3;
        im = (im * temp - re * temp2) / temp3;
        re = temp4;
        
        return this;

    }
    
    /*
     *  z / Real
     */
    public final Complex divide(double number) {
        
        return new Complex(re / number, im / number);
        
    }
    
    /*
     * z = z / Real
     */
    public final Complex divide_mutable(double number) {
        
        re = re / number;
        im = im / number;
        
        return this;
        
    }
    
    
    
    /*
     *  Real / z
     */
    public final Complex r_divide(double number) {
              
        double temp = number / (re * re + im * im);
        
        return new Complex(re * temp, (-im) * temp);
 
    }
    
    /*
     *  z = Real / z
     */
    public final Complex r_divide_mutable(double number) {
              
        double temp = number / (re * re + im * im);
        
        re = re * temp;
        im = (-im) * temp;
        
        return this;
 
    }
    
    /*
     *  Imaginary / z
     */
    public final Complex i_divide(double number) {

        double temp = number / (re * re + im * im);
        
        return new Complex(im * temp, re * temp);
        
    }
    
    /*
     *  z = Imaginary / z
     */
    public final Complex i_divide_mutable(double number) {

        double temp = number / (re * re + im * im);
        
        double temp2 = im * temp;
        im = re * temp;
        re = temp2;
        
        return this;
        
    }
    
     /*
      *  1 / z
      */
     public final Complex reciprocal() {
         
        double temp = re * re + im * im;
        
        return new Complex(re / temp, (-im) / temp);
        
     }
     
     /*
      *  z = 1 / z
      */
     public final Complex reciprocal_mutable() {
         
        double temp = re * re + im * im;
        
        re = re / temp;
        im = (-im) / temp;
        
        return this;
        
     }

    /*
     *  z^2
     */
    public final Complex square() {

        double temp = re * im;
        
        return new Complex((re + im) * (re - im), temp + temp);
        
    }
    
    /*
     *  z = z^2
     */
    public final Complex square_mutable() {

        double temp = re * im;
        
        re = (re + im) * (re - im);
        im = temp + temp;
        
        return this;
        
    }

     /*
     *  z^3
     */
    public final Complex cube() {

        double temp = re * re;
        double temp2 = im * im;

        return new Complex(re * (temp - 3 * temp2), im * (3 * temp - temp2));
        
    }
    
     /*
     *  z = z^3
     */
    public final Complex cube_mutable() {

        double temp = re * re;
        double temp2 = im * im;

        re = re * (temp - 3 * temp2);
        im = im * (3 * temp - temp2);
        
        return this;
        
    }

     /*
     *  z^4
     */
    public final Complex fourth() {

        double temp = re * re;
        double temp2 = im * im;

        return new Complex(temp * (temp - 6 * temp2) + temp2 * temp2, 4 * re * im * (temp - temp2));

    }
    
    /*
     *  z = z^4
     */
    public final Complex fourth_mutable() {

        double temp = re * re;
        double temp2 = im * im;

        double temp3 = temp * (temp - 6 * temp2) + temp2 * temp2;
        im = 4 * re * im * (temp - temp2);
        re = temp3;
        
        return this;

    }

     /*
     *  z^5
     */
    public final Complex fifth() {

        double temp = re * re;
        double temp2 = im * im;

        return new Complex(re * (temp * temp + temp2 * (5 * temp2 - 10 * temp)), im * (temp2 * temp2 + temp * (5 * temp - 10 * temp2)));

    }
    
    /*
     *  z = z^5
     */
    public final Complex fifth_mutable() {

        double temp = re * re;
        double temp2 = im * im;

        re = re * (temp * temp + temp2 * (5 * temp2 - 10 * temp));
        im = im * (temp2 * temp2 + temp * (5 * temp - 10 * temp2));
        
        return this;

    }

     /*
     *  z^6
     */
    public final Complex sixth() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp2 * temp2;

        return new Complex(temp * (temp * temp + 15 * temp2 * (temp2 - temp)) - temp3 * temp2, re * im * (temp * (6 * temp - 20 * temp2) + 6 * temp3));

    }
    
    /*
     *  z = z^6
     */
    public final Complex sixth_mutable() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp2 * temp2;

        double temp4 = temp * (temp * temp + 15 * temp2 * (temp2 - temp)) - temp3 * temp2;
        im = re * im * (temp * (6 * temp - 20 * temp2) + 6 * temp3);
        re = temp4;
        
        return this;

    }

     /*
     *  z^7
     */
    public final Complex seventh() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp2 * temp2;

        return new Complex(re * (temp * temp * temp + temp2 * (temp * (35 * temp2 - 21 * temp) - 7 * temp3)), im * (temp * (temp * (7 * temp - 35 * temp2) + 21 * temp3) - temp3 * temp2));

    }
    
     /*
     *  z = z^7
     */
    public final Complex seventh_mutable() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp2 * temp2;

        re = re * (temp * temp * temp + temp2 * (temp * (35 * temp2 - 21 * temp) - 7 * temp3));
        im = im * (temp * (temp * (7 * temp - 35 * temp2) + 21 * temp3) - temp3 * temp2);
        
        return this;

    }

     /*
     *  z^8
     */
    public final Complex eighth() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp * temp;
        double temp4 = temp2 * temp2;

        return new Complex(temp * (temp3 * temp + 28 * temp2 * (temp * (2.5 * temp2 - temp) - temp4)) + temp4 * temp4, 8 * re * im * (temp * (7 * temp2 * (temp2 - temp) + temp3) - temp4 * temp2));

    }
    
    /*
     *  z = z^8
     */
    public final Complex eighth_mutable() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp * temp;
        double temp4 = temp2 * temp2;

        double temp5 = temp * (temp3 * temp + 28 * temp2 * (temp * (2.5 * temp2 - temp) - temp4)) + temp4 * temp4;
        im = 8 * re * im * (temp * (7 * temp2 * (temp2 - temp) + temp3) - temp4 * temp2);
        re = temp5;
        
        return this;

    }

     /*
     *  z^9
     */
    public final Complex ninth() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp * temp;
        double temp4 = temp2 * temp2;

        return new Complex(re * (im * (im * (re * (re * (im * (im * (126 * temp - 84 * temp2)) - 36 * temp3)) + 9 * temp4 * temp2)) + temp3 * temp3), im * (re * (re * (im * (im * (re * (re * (126 * temp2 - 84 * temp)) - 36 * temp4)) + 9 * temp3 * temp)) + temp4 * temp4));

    }
    
    /*
     *  z = z^9
     */
    public final Complex ninth_mutable() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp * temp;
        double temp4 = temp2 * temp2;

        
        double temp5  = re * (im * (im * (re * (re * (im * (im * (126 * temp - 84 * temp2)) - 36 * temp3)) + 9 * temp4 * temp2)) + temp3 * temp3);
        im = im * (re * (re * (im * (im * (re * (re * (126 * temp2 - 84 * temp)) - 36 * temp4)) + 9 * temp3 * temp)) + temp4 * temp4);
        re = temp5;
        
        return this;

    }

     /*
     *  z^10
     */
    public final Complex tenth() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp * temp;
        double temp4 = temp2 * temp2;
        double temp5 = temp3 * temp;
        double temp6 = temp4 * temp2;

        return new Complex(temp * (temp5 * temp + temp2 * (temp * (210 * temp2 * (temp - temp2) - 45 * temp3) + 45 * temp6)) - temp6 * temp4, 10 * re * im * (temp * (12 * temp2 * (temp * (2.1 * temp2 - temp) - temp4) + temp5) + temp6 * temp2));

    }
    
    /*
     *  z = z^10
     */
    public final Complex tenth_mutable() {

        double temp = re * re;
        double temp2 = im * im;
        double temp3 = temp * temp;
        double temp4 = temp2 * temp2;
        double temp5 = temp3 * temp;
        double temp6 = temp4 * temp2;

        double temp7 = temp * (temp5 * temp + temp2 * (temp * (210 * temp2 * (temp - temp2) - 45 * temp3) + 45 * temp6)) - temp6 * temp4;
        im = 10 * re * im * (temp * (12 * temp2 * (temp * (2.1 * temp2 - temp) - temp4) + temp5) + temp6 * temp2);
        re = temp7;
        
        return this;

    }
   
     /*
     *  |z|^2
     */
    public final double norm_squared() {

        return re * re + im * im;
       
    }
    
     /*
     *  |z|
     */
    public final double norm() {

        return Math.sqrt(re * re + im * im);
       
    }
    
    /*
     *  |z1 - z2|^2
     */
    public final double distance_squared(Complex z) {
        
        double temp_re = re - z.re;
        double temp_im = im - z.im;
        
        return temp_re * temp_re + temp_im * temp_im;
        
    }
    
    /*
     *  <z
     */
    public final double arg() {
         
         return Math.atan2(im, re);
         
    }
    
    /*
     *  |z - Real|^2
     */
    public final double distance_squared(double number) {
        
        double temp_re = re - number;
        
        return temp_re * temp_re + im * im;
        
    }
    
     /*
     *  |z1 - z2|
     */
    public final double distance(Complex z) {
        
        double temp_re = re - z.re;
        double temp_im = im - z.im;
        
        return Math.sqrt(temp_re * temp_re + temp_im * temp_im);
        
    }
    
     /*
     *  |z - Real|
     */
    public final double distance(double number) {
        
        double temp_re = re - number;
        
        return Math.sqrt(temp_re * temp_re + im * im);
        
    }

     /*
     *  |Real|
     */
    public final double absRe() {

        return re >= 0 ? re : -re;

    }

     /*
     *  |Imaginary|
     */
    public final double absIm() {

        return im >= 0 ? im : -im;

    }
    
    /*
     *  abs(z)
     */
    public final Complex abs() {
        
        return new Complex(re >= 0 ? re : -re, im >= 0 ? im : -im);
        
    }
    
    /*
     *  z = abs(z)
     */
    public final Complex abs_mutable() {
        
        re = re >= 0 ? re : -re;
        im = im >= 0 ? im : -im;
        
        return this;
        
    }

     /*
     *  Real -Imaginary i
     */
    public final Complex conjugate() {

        return new Complex(re, -im);
        
    }
    
    
    /*
     *  z = Real -Imaginary i
     */
    public final Complex conjugate_mutable() {

        im = -im;
        
        return this;
        
    }
    
    
    /*
     *  -z
     */
     public final Complex negative() {
         
         return new Complex(-re, -im);
         
     }
     
     /*
     *  z = -z
     */
     public final Complex negative_mutable() {
         
         re = -re;
         im = -im;
         
         return this;
         
     }

     /*
     *  z^n
     */
    public final Complex pow(double exponent) {

	double temp = Math.pow(re * re + im * im, exponent * 0.5);
	double temp2 = exponent * Math.atan2(im, re);

        return new Complex(temp * Math.cos(temp2), temp * Math.sin(temp2));

    }
    
     /*
     *  z^n
     */
    public final Complex pow_mutable(double exponent) {

	double temp = Math.pow(re * re + im * im, exponent * 0.5);
	double temp2 = exponent * Math.atan2(im, re);

        re = temp * Math.cos(temp2);
        im = temp * Math.sin(temp2);
        
        return this;

    }
    
     /*
      *  z1 ^ z2 = exp(z2 * log(z1))
      */
     public final Complex pow(Complex z) { 
       
        return (z.times(this.log())).exp();
         
     }
      
    
    /*
     *  log(z) = ln|z| + arctan(Im/Re)i
     */
    public final Complex log() {
        
        return new Complex(Math.log(re * re + im * im) * 0.5, Math.atan2(im, re));
        
    }
    
    /*
     *  z = log(z) = ln|z| + arctan(Im/Re)i
     */
    public final Complex log_mutable() {
        
        double temp = Math.log(re * re + im * im) * 0.5;
        im = Math.atan2(im, re);
        re = temp;
        
        return this;
        
    }
    
    /*
     *  cos(z) = (exp(iz) + exp(-iz)) / 2
     */
    public final Complex cos() {
        
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
     public final Complex cosh() {
        
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
     public final Complex acos() {
        
         return this.asin().r_sub(pi_2);
         
     }
     
     /*
      *  acosh(z) = log(z + sqrt(z^2 - 1))
      */
     public final Complex acosh() {
        
         return this.plus((this.square().sub(1)).sqrt()).log();
         
     }
    
    /*
     *  sin(z) = (exp(iz) - exp(-iz)) / 2i
     */
    public final Complex sin() {
        
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
    public final Complex sinh() {
        
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
     public final Complex asin() {
        
         return this.times_i(1).plus((this.square().r_sub(1)).sqrt()).log().times_i(-1);
         
     }
     
     /*
      *  asinh(z) = log(z + sqrt(z^2 + 1))
      */
     public final Complex asinh() {
        
         return this.plus((this.square().plus(1)).sqrt()).log();
         
     }
        
    /*
     *  tan(z) = (1 - exp(-2zi)) / i(1 + exp(-2zi))
     */
    public final Complex tan() {
        
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
    public final Complex tanh() {
    
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
     public final Complex atan() {
        
         Complex temp = this.times_i(1);
         
         return ((temp.r_sub(1)).divide(temp.plus(1))).log().times_i(0.5);
         
     }
     
     /*
      *  atanh(z) = (1 / 2)log((z + 1) / (1 - z))
      */
     public final Complex atanh() {
   
         return ((this.plus(1)).divide(this.r_sub(1))).log().times(0.5);
         
     }
    
    /*
     *  cot(z) = i(1 + exp(-2zi)) / (1 - exp(-2zi))
     */
    public final Complex cot() {

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
    public final Complex coth() {
        
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
     public final Complex acot() {
        
         Complex temp = this.times_i(1);
         Complex temp2 = this.square();
         
         return ((temp2.sub(temp)).divide(temp2.plus(temp))).log().times_i(0.5);
         
     }
     
     /*
      *  acoth(z) = (1 / 2)log((1 + 1/z) / (1 - 1/z))
      */
     public final Complex acoth() {
        
         Complex temp = this.reciprocal();
         
         return ((temp.plus(1)).divide(temp.r_sub(1))).log().times(0.5);
         
     }
     
     /*
      *  sec(z) = 1 / cos(z)
      */
     public final Complex sec() {

         return this.cos().reciprocal();
         
     }
     
     /*
      *  asec(z) = pi / 2 + ilog(sqrt(1 - 1 / z^2) + i / z)
      */
     public final Complex asec() {

         return (((this.square().reciprocal()).r_sub(1).sqrt()).plus(this.i_divide(1))).log().times_i(1).plus(pi_2);
         
     }
     
     /*
      *  sech(z) = 1 / cosh(z)
      */
     public final Complex sech() {

         return this.cosh().reciprocal();
         
     }
     
     /*
      *  asech(z) = log(sqrt(1 / z^2 - 1) + 1 / z)
      */
     public final Complex asech() {

         return (((this.square().reciprocal()).sub(1).sqrt()).plus(this.reciprocal())).log();
         
     }
     
     /*
      *  csc(z) = 1 / sin(z)
      */
     public final Complex csc() {

         return this.sin().reciprocal();
         
     }
     
     /*
      *  acsc(z) = -ilog(sqrt(1 - 1 / z^2) + i / z)
      */
     public final Complex acsc() {

         return (((this.square().reciprocal()).r_sub(1).sqrt()).plus(this.i_divide(1))).log().times_i(-1);
         
     }
     
     /*
      *  csch(z) = 1 / sinh(z)
      */
     public final Complex csch() {

         return this.sinh().reciprocal();
         
     }
     
     /*
      *  acsch(z) = log(sqrt(1 / z^2 + 1) + 1 / z)
      */
     public final Complex acsch() {

         return (((this.square().reciprocal()).plus(1).sqrt()).plus(this.reciprocal())).log();
         
     }
 
     /*
      *  exp(z) = exp(Re(z)) * (cos(Im(z)) + sin(Im(z))i)
      */
     public final Complex exp() {
         
        double temp = Math.exp(re);

        return new Complex(temp * Math.cos(im), temp * Math.sin(im)); 

     }
     
     
     /*
      * sqrt(z) = z^0.5
      */
     public final Complex sqrt() {
         
        double temp = Math.pow(re * re + im * im, 0.25);
	double temp2 = 0.5 * Math.atan2(im, re);

        return new Complex(temp * Math.cos(temp2), temp * Math.sin(temp2));
        
     }
     
     /*
      * z = sqrt(z) = z^0.5
      */
     public final Complex sqrt_mutable() {
         
        double temp = Math.pow(re * re + im * im, 0.25);
	double temp2 = 0.5 * Math.atan2(im, re);

        re = temp * Math.cos(temp2);
        im = temp * Math.sin(temp2);
        
        return this;
        
     }
     
     /*
     *  sin, (sin)'
     */
     public final Complex[] der01_sin() {
        
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
     public final Complex[] der012_sin() {
        
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
     public final Complex[] der01_cos() {
        
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
     public final Complex[] der012_cos() {
        
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
     public final Complex gaussian_integer() {
         
         return new Complex((int)(re < 0 ? re - 0.5 : re + 0.5), (int)(im < 0 ? im - 0.5 : im + 0.5));
         
     }
     
     /*
      *  z = The closest Gaussian Integer to the Complex number
      */
     public final Complex gaussian_integer_mutable() {
         
         re = (int)(re < 0 ? re - 0.5 : re + 0.5);
         im = (int)(im < 0 ? im - 0.5 : im + 0.5);
         
         return this;
         
     }
     
     
     /*
      *  lexicographical comparison between two complex numbers
      */
     public final int compare(Complex z2) {
         
         if(re > z2.re) {
             return -1;
         }
         else if(re < z2.re) {
             return 1;
         }
         else if(im > z2.im) {
             return -1;
         }
         else if(z2. im > im) {
             return 1;
         }
         
         return 0;
     }
     
     @Override
     public final String toString() {
         
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
     
     
     public final Complex fold_out(double number) {
         
         double norm = re * re + im * im;

         return norm > number ? this.divide(norm) : this;
         
     }
     
     public final Complex fold_in(double number) {
         
         double norm = re * re + im * im;
         
         return norm < number ? this.divide(norm) : this;
         
     } 

     public final Complex fold_right(double number) {

         return re < number ? new Complex(re + 2 * (number - re), im) : this;
         
     }
     
     public final Complex fold_left(double number) {

         return re > number ? new Complex(re - 2 * (re - number), im) : this;
         
     }
     
     public final Complex fold_up(double number) {

         return im < number ? new Complex(re, im + 2 * (number - im)) : this;
         
     }
     
     public final Complex fold_down(double number) {

         return im > number ? new Complex(re, im - 2 * (im - number)) : this;
         
     }
         
     
}
    

