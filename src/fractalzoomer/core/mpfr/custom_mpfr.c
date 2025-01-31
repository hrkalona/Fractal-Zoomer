#define MPFR_NEED_LONGLONG_H
#include "mpfr-impl.h"

//add this to mpfr.h
//__MPFR_DECLSPEC void mpfr_fz_square_plus_c (mpfr_ptr re, mpfr_ptr im, mpfr_ptr temp, mpfr_srcptr re_sqr, mpfr_srcptr im_sqr, mpfr_srcptr norm_sqr, mpfr_srcptr cre, mpfr_srcptr cim, mpfr_rnd_t rnd_mode);
//__MPFR_DECLSPEC void mpfr_fz_square (mpfr_ptr re, mpfr_ptr im, mpfr_ptr temp, mpfr_srcptr re_sqr, mpfr_srcptr im_sqr, mpfr_srcptr norm_sqr, mpfr_rnd_t rnd_mode);
//__MPFR_DECLSPEC void mpfr_fz_norm_square_with_components (mpfr_ptr re_sqr, mpfr_ptr im_sqr, mpfr_ptr norm_sqr, mpfr_srcptr re, mpfr_srcptr im, mpfr_rnd_t rnd_mode, int use_threads);
//__MPFR_DECLSPEC void mpfr_fz_get_d (double* valRe, double* valIm, mpfr_srcptr re, mpfr_srcptr im, mpfr_rnd_t rnd_mode);
//__MPFR_DECLSPEC void mpfr_fz_get_d_2exp (double* valRe, double* valIm, long *expRe, long* expIm, mpfr_srcptr re, mpfr_srcptr im, mpfr_rnd_t rnd_mode);
//__MPFR_DECLSPEC void mpfr_fz_square_plus_c_simple (mpfr_ptr re, mpfr_ptr im, mpfr_ptr temp1, mpfr_ptr temp2, mpfr_ptr temp3, mpfr_srcptr cre, mpfr_srcptr cim, mpfr_rnd_t rnd_mode, int algorithm, int use_threads);
//__MPFR_DECLSPEC void mpfr_fz_square_plus_c_simple_with_reduction_not_deep (mpfr_ptr re, mpfr_ptr im, mpfr_ptr temp1, mpfr_ptr temp2, mpfr_ptr temp3, mpfr_srcptr cre, mpfr_srcptr cim, mpfr_rnd_t rnd_mode, int algorithm, int use_threads, double* valRe, double* valIm);
//__MPFR_DECLSPEC void mpfr_fz_square_plus_c_simple_with_reduction_deep (mpfr_ptr re, mpfr_ptr im, mpfr_ptr temp1, mpfr_ptr temp2, mpfr_ptr temp3, mpfr_srcptr cre, mpfr_srcptr cim, mpfr_rnd_t rnd_mode, int algorithm, int use_threads, double* valRe, double* valIm, long* expRe, long* expIm);
//__MPFR_DECLSPEC void mpfr_fz_set (mpfr_ptr destre, mpfr_ptr destim, mpfr_srcptr srcre, mpfr_srcptr srcim, mpfr_rnd_t rnd_mode);
//__MPFR_DECLSPEC void mpfr_fz_norm_square (mpfr_ptr norm_sqr, mpfr_ptr temp1, mpfr_srcptr re, mpfr_srcptr im, mpfr_rnd_t rnd_mode, int use_threads);
//__MPFR_DECLSPEC void mpfr_fz_self_add (mpfr_ptr re, mpfr_ptr im, mpfr_srcptr val_re, mpfr_srcptr val_im, mpfr_rnd_t rnd_mode);
//__MPFR_DECLSPEC void mpfr_fz_self_sub (mpfr_ptr re, mpfr_ptr im, mpfr_srcptr val_re, mpfr_srcptr val_im, mpfr_rnd_t rnd_mode);
//__MPFR_DECLSPEC void mpfr_fz_rotation (mpfr_ptr x, mpfr_ptr y, mpfr_ptr temp_re, mpfr_ptr temp_im, mpfr_ptr f, mpfr_srcptr a, mpfr_srcptr asb, mpfr_srcptr apb, mpfr_rnd_t rnd_mode);
//__MPFR_DECLSPEC void mpfr_fz_AsBmC (mpfr_ptr temp, mpfr_srcptr a, mpfr_srcptr b, int c, mpfr_rnd_t rnd_mode);
//__MPFR_DECLSPEC void mpfr_fz_ApBmC (mpfr_ptr temp, mpfr_srcptr a, mpfr_srcptr b, int c, mpfr_rnd_t rnd_mode);
//__MPFR_DECLSPEC void mpfr_fz_ApBmC_DsEmG (mpfr_ptr temp, mpfr_ptr temp2, mpfr_srcptr a, mpfr_srcptr b, double c, mpfr_srcptr d, mpfr_srcptr e, double g, mpfr_rnd_t rnd_mode);
//__MPFR_DECLSPEC void mpfr_fz_ApBmC_DpEmG (mpfr_ptr temp, mpfr_ptr temp2, mpfr_srcptr a, mpfr_srcptr b, mpfr_srcptr c, mpfr_srcptr d, mpfr_srcptr e, mpfr_srcptr g, mpfr_rnd_t rnd_mode);
//__MPFR_DECLSPEC void mpfr_fz_r_ball_pow2 (mpfr_ptr r, mpfr_ptr az, mpfr_srcptr r0, mpfr_srcptr azsquare, mpfr_rnd_t rnd_mode);
//Also dont forget to modify the Makefiles in src/ and add custom_mpfr to every place required

MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_square_plus_c (mpfr_ptr re, mpfr_ptr im, mpfr_ptr temp, mpfr_srcptr re_sqr, mpfr_srcptr im_sqr, mpfr_srcptr norm_sqr, mpfr_srcptr cre, mpfr_srcptr cim, mpfr_rnd_t rnd_mode)
{
    mpfr_add(temp, re, im, rnd_mode); // temp = re + im
    mpfr_sqr(temp, temp, rnd_mode); // temp = (re + im)^2
    mpfr_sub(im, temp, norm_sqr, rnd_mode); // im = (re + im)^2 - re^2 - im^2
    mpfr_add(im, im, cim, rnd_mode); // im = (re + im)^2 - re^2 - im^2 + cim
    
    mpfr_sub(re, re_sqr, im_sqr, rnd_mode); // re = re^2 - im^2
    mpfr_add(re, re, cre, rnd_mode); // re = re^2 - im^2 + cre
}

MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_square (mpfr_ptr re, mpfr_ptr im, mpfr_ptr temp, mpfr_srcptr re_sqr, mpfr_srcptr im_sqr, mpfr_srcptr norm_sqr, mpfr_rnd_t rnd_mode)
{
    mpfr_add(temp, re, im, rnd_mode); // temp = re + im
    mpfr_sqr(temp, temp, rnd_mode); // temp = (re + im)^2
    mpfr_sub(im, temp, norm_sqr, rnd_mode); // im = (re + im)^2 - re^2 - im^2
    
    mpfr_sub(re, re_sqr, im_sqr, rnd_mode); // re = re^2 - im^2
}


MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_square_plus_c_simple (mpfr_ptr re, mpfr_ptr im, mpfr_ptr temp1, mpfr_ptr temp2, mpfr_ptr temp3, mpfr_srcptr cre, mpfr_srcptr cim, mpfr_rnd_t rnd_mode, int algorithm, int use_threads)
{
    if(algorithm == 0) {
        if(use_threads) {
           #pragma omp parallel sections num_threads(2)
           {
               #pragma omp section
               {
                   mpfr_add(temp1, re, im, rnd_mode); // temp1 = re + im
                   mpfr_sub(temp2, re, im, rnd_mode); // temp2 = re - im
                   mpfr_mul(temp2, temp1, temp2, rnd_mode); // temp2 = (re + im) * (re - im)
               }
               #pragma omp section
               {
                   mpfr_mul(temp3, re, im, rnd_mode); // temp3 = re * im
                   mpfr_mul_2ui(temp3, temp3, 1, rnd_mode); // temp3 = 2 * re * im
               }
           }
        }
        else {
            mpfr_add(temp1, re, im, rnd_mode); // temp1 = re + im
            mpfr_sub(temp2, re, im, rnd_mode); // temp2 = re - im
            mpfr_mul(temp2, temp1, temp2, rnd_mode); // temp2 = (re + im) * (re - im)

            mpfr_mul(temp3, re, im, rnd_mode); // temp3 = re * im
            mpfr_mul_2ui(temp3, temp3, 1, rnd_mode); // temp3 = 2 * re * im
        }

        mpfr_add(re, temp2, cre, rnd_mode); // re = (re + im) * (re - im) + cre
        mpfr_add(im, temp3, cim, rnd_mode); // im = 2 * re * im + cim
    }
    else {
        if(use_threads) {
            #pragma omp parallel sections num_threads(3)
            {
               #pragma omp section
               {
                   mpfr_sqr(temp1, re, rnd_mode); // re^2
               }
               #pragma omp section
               {
                   mpfr_sqr(temp2, im, rnd_mode); // im^2
               }
               #pragma omp section
               {
                   mpfr_add(temp3, re, im, rnd_mode); // temp3 = re + im
                   mpfr_sqr(temp3, temp3, rnd_mode); // temp3 = (re + im)^2
               }
            }
        }
        else {
            mpfr_sqr(temp1, re, rnd_mode); // re^2
            mpfr_sqr(temp2, im, rnd_mode); // im^2

            mpfr_add(temp3, re, im, rnd_mode); // temp3 = re + im
            mpfr_sqr(temp3, temp3, rnd_mode); // temp3 = (re + im)^2
        }

        mpfr_sub(re, temp1, temp2, rnd_mode); // re = re^2 - im^2
        mpfr_add(re, re, cre, rnd_mode); // re = re^2 - im^2 + cre

        mpfr_add(temp1, temp1, temp2, rnd_mode); // temp1 = re^2 + im^2

        mpfr_sub(im, temp3, temp1, rnd_mode); // im = (re + im)^2 - re^2 - im^2
        mpfr_add(im, im, cim, rnd_mode); // im = (re + im)^2 - re^2 - im^2 + cim
    }
}

MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_square_plus_c_simple_with_reduction_deep (mpfr_ptr re, mpfr_ptr im, mpfr_ptr temp1, mpfr_ptr temp2, mpfr_ptr temp3, mpfr_srcptr cre, mpfr_srcptr cim, mpfr_rnd_t rnd_mode, int algorithm, int use_threads, double* valRe, double* valIm, long* expRe, long* expIm)
{
    if(algorithm == 0) {
        if(use_threads) {
           #pragma omp parallel sections num_threads(2)
           {
               #pragma omp section
               {
                   mpfr_add(temp1, re, im, rnd_mode); // temp1 = re + im
                   mpfr_sub(temp2, re, im, rnd_mode); // temp2 = re - im
                   mpfr_mul(temp2, temp1, temp2, rnd_mode); // temp2 = (re + im) * (re - im)
               }
               #pragma omp section
               {
                   mpfr_mul(temp3, re, im, rnd_mode); // temp3 = re * im
                   mpfr_mul_2ui(temp3, temp3, 1, rnd_mode); // temp3 = 2 * re * im
               }
           }
        }
        else {
            mpfr_add(temp1, re, im, rnd_mode); // temp1 = re + im
            mpfr_sub(temp2, re, im, rnd_mode); // temp2 = re - im
            mpfr_mul(temp2, temp1, temp2, rnd_mode); // temp2 = (re + im) * (re - im)

            mpfr_mul(temp3, re, im, rnd_mode); // temp3 = re * im
            mpfr_mul_2ui(temp3, temp3, 1, rnd_mode); // temp3 = 2 * re * im
        }

        mpfr_add(re, temp2, cre, rnd_mode); // re = (re + im) * (re - im) + cre
        mpfr_add(im, temp3, cim, rnd_mode); // im = 2 * re * im + cim
    }
    else {
        if(use_threads) {
            #pragma omp parallel sections num_threads(3)
            {
               #pragma omp section
               {
                   mpfr_sqr(temp1, re, rnd_mode); // re^2
               }
               #pragma omp section
               {
                   mpfr_sqr(temp2, im, rnd_mode); // im^2
               }
               #pragma omp section
               {
                   mpfr_add(temp3, re, im, rnd_mode); // temp3 = re + im
                   mpfr_sqr(temp3, temp3, rnd_mode); // temp3 = (re + im)^2
               }
            }
        }
        else {
            mpfr_sqr(temp1, re, rnd_mode); // re^2
            mpfr_sqr(temp2, im, rnd_mode); // im^2

            mpfr_add(temp3, re, im, rnd_mode); // temp3 = re + im
            mpfr_sqr(temp3, temp3, rnd_mode); // temp3 = (re + im)^2
        }

        mpfr_sub(re, temp1, temp2, rnd_mode); // re = re^2 - im^2
        mpfr_add(re, re, cre, rnd_mode); // re = re^2 - im^2 + cre

        mpfr_add(temp1, temp1, temp2, rnd_mode); // temp1 = re^2 + im^2

        mpfr_sub(im, temp3, temp1, rnd_mode); // im = (re + im)^2 - re^2 - im^2
        mpfr_add(im, im, cim, rnd_mode); // im = (re + im)^2 - re^2 - im^2 + cim
    }
    
    *valRe = mpfr_get_d_2exp(expRe, re, rnd_mode);
    *valIm = mpfr_get_d_2exp(expIm, im, rnd_mode); 
}

MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_square_plus_c_simple_with_reduction_not_deep (mpfr_ptr re, mpfr_ptr im, mpfr_ptr temp1, mpfr_ptr temp2, mpfr_ptr temp3, mpfr_srcptr cre, mpfr_srcptr cim, mpfr_rnd_t rnd_mode, int algorithm, int use_threads, double* valRe, double* valIm)
{
    if(algorithm == 0) {
        if(use_threads) {
           #pragma omp parallel sections num_threads(2)
           {
               #pragma omp section
               {
                   mpfr_add(temp1, re, im, rnd_mode); // temp1 = re + im
                   mpfr_sub(temp2, re, im, rnd_mode); // temp2 = re - im
                   mpfr_mul(temp2, temp1, temp2, rnd_mode); // temp2 = (re + im) * (re - im)
               }
               #pragma omp section
               {
                   mpfr_mul(temp3, re, im, rnd_mode); // temp3 = re * im
                   mpfr_mul_2ui(temp3, temp3, 1, rnd_mode); // temp3 = 2 * re * im
               }
           }
        }
        else {
            mpfr_add(temp1, re, im, rnd_mode); // temp1 = re + im
            mpfr_sub(temp2, re, im, rnd_mode); // temp2 = re - im
            mpfr_mul(temp2, temp1, temp2, rnd_mode); // temp2 = (re + im) * (re - im)

            mpfr_mul(temp3, re, im, rnd_mode); // temp3 = re * im
            mpfr_mul_2ui(temp3, temp3, 1, rnd_mode); // temp3 = 2 * re * im
        }

        mpfr_add(re, temp2, cre, rnd_mode); // re = (re + im) * (re - im) + cre
        mpfr_add(im, temp3, cim, rnd_mode); // im = 2 * re * im + cim
    }
    else {
        if(use_threads) {
            #pragma omp parallel sections num_threads(3)
            {
               #pragma omp section
               {
                   mpfr_sqr(temp1, re, rnd_mode); // re^2
               }
               #pragma omp section
               {
                   mpfr_sqr(temp2, im, rnd_mode); // im^2
               }
               #pragma omp section
               {
                   mpfr_add(temp3, re, im, rnd_mode); // temp3 = re + im
                   mpfr_sqr(temp3, temp3, rnd_mode); // temp3 = (re + im)^2
               }
            }
        }
        else {
            mpfr_sqr(temp1, re, rnd_mode); // re^2
            mpfr_sqr(temp2, im, rnd_mode); // im^2

            mpfr_add(temp3, re, im, rnd_mode); // temp3 = re + im
            mpfr_sqr(temp3, temp3, rnd_mode); // temp3 = (re + im)^2
        }

        mpfr_sub(re, temp1, temp2, rnd_mode); // re = re^2 - im^2
        mpfr_add(re, re, cre, rnd_mode); // re = re^2 - im^2 + cre

        mpfr_add(temp1, temp1, temp2, rnd_mode); // temp1 = re^2 + im^2

        mpfr_sub(im, temp3, temp1, rnd_mode); // im = (re + im)^2 - re^2 - im^2
        mpfr_add(im, im, cim, rnd_mode); // im = (re + im)^2 - re^2 - im^2 + cim
    }
    
    *valRe = mpfr_get_d(re, rnd_mode); 
    *valIm = mpfr_get_d(im, rnd_mode); 
}

MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_norm_square_with_components (mpfr_ptr re_sqr, mpfr_ptr im_sqr, mpfr_ptr norm_sqr, mpfr_srcptr re, mpfr_srcptr im, mpfr_rnd_t rnd_mode, int use_threads)
{
    if(use_threads) {
       #pragma omp parallel sections num_threads(2)
        {
           #pragma omp section
           {
               mpfr_sqr(re_sqr, re, rnd_mode); // re^2
           }
           #pragma omp section
           {
               mpfr_sqr(im_sqr, im, rnd_mode); // im^2
           }
        }
    }
    else {
        mpfr_sqr(re_sqr, re, rnd_mode); // re^2
        mpfr_sqr(im_sqr, im, rnd_mode); // im^2
    }

    mpfr_add(norm_sqr, re_sqr, im_sqr, rnd_mode); // re^2 + im^2
}

MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_norm_square (mpfr_ptr norm_sqr, mpfr_ptr temp1, mpfr_srcptr re, mpfr_srcptr im, mpfr_rnd_t rnd_mode, int use_threads)
{
    if(use_threads) {
        #pragma omp parallel sections num_threads(2)
        {
           #pragma omp section
           {
               mpfr_sqr(norm_sqr, re, rnd_mode); // re^2
           }
           #pragma omp section
           {
               mpfr_sqr(temp1, im, rnd_mode); // im^2
           }
        }
    }
    else {
        mpfr_sqr(norm_sqr, re, rnd_mode); // re^2
        mpfr_sqr(temp1, im, rnd_mode); // im^2
    }

    mpfr_add(norm_sqr, norm_sqr, temp1, rnd_mode); // re^2 + im^2
}

MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_get_d (double* valRe, double* valIm, mpfr_srcptr re, mpfr_srcptr im, mpfr_rnd_t rnd_mode)
{
    *valRe = mpfr_get_d(re, rnd_mode); 
    *valIm = mpfr_get_d(im, rnd_mode); 
}

MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_get_d_2exp (double* valRe, double* valIm, long *expRe, long* expIm, mpfr_srcptr re, mpfr_srcptr im, mpfr_rnd_t rnd_mode)
{
    *valRe = mpfr_get_d_2exp(expRe, re, rnd_mode);
    *valIm = mpfr_get_d_2exp(expIm, im, rnd_mode); 
}

MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_set (mpfr_ptr destre, mpfr_ptr destim, mpfr_srcptr srcre, mpfr_srcptr srcim, mpfr_rnd_t rnd_mode)
{
    mpfr_set(destre, srcre, rnd_mode);
    mpfr_set(destim, srcim, rnd_mode); 
}


MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_self_add (mpfr_ptr re, mpfr_ptr im, mpfr_srcptr val_re, mpfr_srcptr val_im, mpfr_rnd_t rnd_mode)
{
	mpfr_add(re, re, val_re, rnd_mode);
	mpfr_add(im, im, val_im, rnd_mode);
}

MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_self_sub (mpfr_ptr re, mpfr_ptr im, mpfr_srcptr val_re, mpfr_srcptr val_im, mpfr_rnd_t rnd_mode)
{
	mpfr_sub(re, re, val_re, rnd_mode);
	mpfr_sub(im, im, val_im, rnd_mode);
}

MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_rotation (mpfr_ptr x, mpfr_ptr y, mpfr_ptr temp_re, mpfr_ptr temp_im, mpfr_ptr f, mpfr_srcptr a, mpfr_srcptr asb, mpfr_srcptr apb, mpfr_rnd_t rnd_mode)
{
	mpfr_sub(f, x, y, rnd_mode);
	mpfr_mul(f, a, f, rnd_mode);
	
	mpfr_mul(temp_re, asb, y, rnd_mode);
	mpfr_mul(temp_im, apb, x, rnd_mode);
	
	mpfr_add(x, temp_re, f, rnd_mode);
	mpfr_sub(y, temp_im, f, rnd_mode);
}


MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_AsBmC (mpfr_ptr temp, mpfr_srcptr a, mpfr_srcptr b, int c, mpfr_rnd_t rnd_mode)
{
	mpfr_mul_si(temp, b, c, rnd_mode);
	mpfr_sub(temp, a, temp, rnd_mode);
}

MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_ApBmC (mpfr_ptr temp, mpfr_srcptr a, mpfr_srcptr b, int c, mpfr_rnd_t rnd_mode)
{
	mpfr_mul_si(temp, b, c, rnd_mode);
	mpfr_add(temp, a, temp, rnd_mode);
}

MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_ApBmC_DsEmG (mpfr_ptr temp, mpfr_ptr temp2, mpfr_srcptr a, mpfr_srcptr b, double c, mpfr_srcptr d, mpfr_srcptr e, double g, mpfr_rnd_t rnd_mode)
{
	mpfr_mul_d(temp, b, c, rnd_mode);
	mpfr_add(temp, a, temp, rnd_mode);
	
	mpfr_mul_d(temp2, e, g, rnd_mode);
	mpfr_sub(temp2, d, temp2, rnd_mode);
}


MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_ApBmC_DpEmG (mpfr_ptr temp, mpfr_ptr temp2, mpfr_srcptr a, mpfr_srcptr b, mpfr_srcptr c, mpfr_srcptr d, mpfr_srcptr e, mpfr_srcptr g, mpfr_rnd_t rnd_mode)
{
	mpfr_mul(temp, b, c, rnd_mode);
	mpfr_add(temp, a, temp, rnd_mode);
	
	mpfr_mul(temp2, e, g, rnd_mode);
	mpfr_add(temp2, d, temp2, rnd_mode);
}

MPFR_HOT_FUNCTION_ATTR void
mpfr_fz_r_ball_pow2 (mpfr_ptr r, mpfr_ptr az, mpfr_srcptr r0, mpfr_srcptr azsquare, mpfr_rnd_t rnd_mode)
{
    mpfr_add(az, r, az, rnd_mode); // az = az + r
    mpfr_sqr(r, az, rnd_mode); // r = (az + r)^2
    mpfr_sub(r, r, azsquare, rnd_mode);
    mpfr_add(r, r, r0, rnd_mode);
}
