#include "mpir.h"
#include "gmp-impl.h"

//Add to gmp-h.in
/*__GMP_DECLSPEC void mpir_fz_square_plus_c(mpf_ptr re, mpf_ptr im, mpf_ptr temp, mpf_srcptr re_sqr, mpf_srcptr im_sqr, mpf_srcptr norm_sqr, mpf_srcptr cre, mpf_srcptr cim);
__GMP_DECLSPEC void mpir_fz_square(mpf_ptr re, mpf_ptr im, mpf_ptr temp, mpf_srcptr re_sqr, mpf_srcptr im_sqr, mpf_srcptr norm_sqr);
__GMP_DECLSPEC void mpir_fz_square_plus_c_simple(mpf_ptr re, mpf_ptr im, mpf_ptr temp1, mpf_ptr temp2, mpf_ptr temp3, mpf_srcptr cre, mpf_srcptr cim, int algorithm, int use_threads);
__GMP_DECLSPEC void mpir_fz_square_plus_c_simple_with_reduction_not_deep(mpf_ptr re, mpf_ptr im, mpf_ptr temp1, mpf_ptr temp2, mpf_ptr temp3, mpf_srcptr cre, mpf_srcptr cim, int algorithm, int use_threads, double* valRe, double* valIm);
__GMP_DECLSPEC void mpir_fz_square_plus_c_simple_with_reduction_deep(mpf_ptr re, mpf_ptr im, mpf_ptr temp1, mpf_ptr temp2, mpf_ptr temp3, mpf_srcptr cre, mpf_srcptr cim, int algorithm, int use_threads, double* mantissaRe, double* mantissaIm, long* expRe, long* expIm);
__GMP_DECLSPEC void mpir_fz_norm_square_with_components(mpf_ptr re_sqr, mpf_ptr im_sqr, mpf_ptr norm_sqr, mpf_srcptr re, mpf_srcptr im, int use_threads);
__GMP_DECLSPEC void mpir_fz_norm_square(mpf_ptr norm_sqr, mpf_ptr temp1, mpf_srcptr re, mpf_srcptr im, int use_threads);
__GMP_DECLSPEC void mpir_fz_get_d(double* valRe, double* valIm, mpf_srcptr re, mpf_srcptr im);
__GMP_DECLSPEC void mpir_fz_get_d_2exp(double* valRe, double* valIm, long* expRe, long* expIm, mpf_srcptr re, mpf_srcptr im);
__GMP_DECLSPEC void mpir_fz_set(mpf_ptr destre, mpf_ptr destim, mpf_srcptr srcre, mpf_srcptr srcim);
__GMP_DECLSPEC void mpir_fz_self_add(mpf_ptr re, mpf_ptr im, mpf_srcptr val_re, mpf_srcptr val_im);
__GMP_DECLSPEC void mpir_fz_self_sub(mpf_ptr re, mpf_ptr im, mpf_srcptr val_re, mpf_srcptr val_im);
__GMP_DECLSPEC void mpir_fz_rotation(mpf_ptr x, mpf_ptr y, mpf_ptr temp_re, mpf_ptr temp_im, mpf_ptr f, mpf_srcptr a, mpf_srcptr asb, mpf_srcptr apb);
__GMP_DECLSPEC void mpir_fz_AsBmC(mpf_ptr temp, mpf_srcptr a, mpf_srcptr b, int c);
__GMP_DECLSPEC void mpir_fz_ApBmC(mpf_ptr temp, mpf_srcptr a, mpf_srcptr b, int c);
__GMP_DECLSPEC void mpir_fz_ApBmC_DsEmG(mpf_ptr temp, mpf_ptr temp2, mpf_srcptr a, mpf_srcptr b, mpf_srcptr c, mpf_srcptr d, mpf_srcptr e, mpf_srcptr g);
__GMP_DECLSPEC void mpir_fz_ApBmC_DpEmG(mpf_ptr temp, mpf_ptr temp2, mpf_srcptr a, mpf_srcptr b, mpf_srcptr c, mpf_srcptr d, mpf_srcptr e, mpf_srcptr g);
__GMP_DECLSPEC void mpir_fz_r_ball_pow2(mpf_ptr r, mpf_ptr az, mpf_srcptr r0, mpf_srcptr azsquare);*/

void mpir_fz_square_plus_c(mpf_ptr re, mpf_ptr im, mpf_ptr temp, mpf_srcptr re_sqr, mpf_srcptr im_sqr, mpf_srcptr norm_sqr, mpf_srcptr cre, mpf_srcptr cim)
{
    mpf_add(temp, re, im); // temp = re + im
    mpf_mul(temp, temp, temp); // temp = (re + im)^2
    mpf_sub(im, temp, norm_sqr); // im = (re + im)^2 - re^2 - im^2
    mpf_add(im, im, cim); // im = (re + im)^2 - re^2 - im^2 + cim
    mpf_sub(re, re_sqr, im_sqr); // re = re^2 - im^2
    mpf_add(re, re, cre); // re = re^2 - im^2 + cre
}

void mpir_fz_square(mpf_ptr re, mpf_ptr im, mpf_ptr temp, mpf_srcptr re_sqr, mpf_srcptr im_sqr, mpf_srcptr norm_sqr)
{
    mpf_add(temp, re, im); // temp = re + im
    mpf_mul(temp, temp, temp); // temp = (re + im)^2
    mpf_sub(im, temp, norm_sqr); // im = (re + im)^2 - re^2 - im^2

    mpf_sub(re, re_sqr, im_sqr); // re = re^2 - im^2
}


void mpir_fz_square_plus_c_simple(mpf_ptr re, mpf_ptr im, mpf_ptr temp1, mpf_ptr temp2, mpf_ptr temp3, mpf_srcptr cre, mpf_srcptr cim, int algorithm, int use_threads)
{

    if (algorithm == 0) {
        if (use_threads) {
            #pragma omp parallel sections num_threads(2)
            {
                #pragma omp section
                {
                    mpf_add(temp1, re, im); // temp1 = re + im
                    mpf_sub(temp2, re, im); // temp2 = re - im
                    mpf_mul(temp2, temp1, temp2); // temp2 = (re + im) * (re - im)
                }
                #pragma omp section
                {
                    mpf_mul(temp3, re, im); // temp3 = re * im
                    mpf_mul_2exp(temp3, temp3, 1); // temp3 = 2 * re * im
                }
            }
        }
        else {
            mpf_add(temp1, re, im); // temp1 = re + im
            mpf_sub(temp2, re, im); // temp2 = re - im
            mpf_mul(temp2, temp1, temp2); // temp2 = (re + im) * (re - im)

            mpf_mul(temp3, re, im); // temp3 = re * im
            mpf_mul_2exp(temp3, temp3, 1); // temp3 = 2 * re * im
        }

        mpf_add(re, temp2, cre); // re = (re + im) * (re - im) + cre
        mpf_add(im, temp3, cim); // im = 2 * re * im + cim
    }
    else {
        if (use_threads) {

            #pragma omp parallel sections num_threads(3)
            {
                #pragma omp section
                {
                    mpf_mul(temp1, re, re); // re^2
                }
                #pragma omp section
                {
                    mpf_mul(temp2, im, im); // im^2
                }
                #pragma omp section
                {
                    mpf_add(temp3, re, im); // temp3 = re + im
                    mpf_mul(temp3, temp3, temp3); // temp3 = (re + im)^2
                }
            }
        }
        else {
            mpf_mul(temp1, re, re); // re^2
            mpf_mul(temp2, im, im); // im^2

            mpf_add(temp3, re, im); // temp3 = re + im
            mpf_mul(temp3, temp3, temp3); // temp3 = (re + im)^2
        }


        mpf_sub(re, temp1, temp2); // re = re^2 - im^2
        mpf_add(re, re, cre); // re = re^2 - im^2 + cre


        mpf_add(temp1, temp1, temp2); // temp1 = re^2 + im^2

        mpf_sub(im, temp3, temp1); // im = (re + im)^2 - re^2 - im^2
        mpf_add(im, im, cim); // im = (re + im)^2 - re^2 - im^2 + cim
    }
    
}


void mpir_fz_square_plus_c_simple_with_reduction_not_deep(mpf_ptr re, mpf_ptr im, mpf_ptr temp1, mpf_ptr temp2, mpf_ptr temp3, mpf_srcptr cre, mpf_srcptr cim, int algorithm, int use_threads, double* valRe, double* valIm)
{
    if (algorithm == 0) {
        if (use_threads) {
            #pragma omp parallel sections num_threads(2)
            {
                #pragma omp section
                {
                   mpf_add(temp1, re, im); // temp1 = re + im
                    mpf_sub(temp2, re, im); // temp2 = re - im
                    mpf_mul(temp2, temp1, temp2); // temp2 = (re + im) * (re - im)
                }
                #pragma omp section
                {
                    mpf_mul(temp3, re, im); // temp3 = re * im
                    mpf_mul_2exp(temp3, temp3, 1); // temp3 = 2 * re * im
                }
            }
        }
        else {
            mpf_add(temp1, re, im); // temp1 = re + im
            mpf_sub(temp2, re, im); // temp2 = re - im
            mpf_mul(temp2, temp1, temp2); // temp2 = (re + im) * (re - im)

            mpf_mul(temp3, re, im); // temp3 = re * im
            mpf_mul_2exp(temp3, temp3, 1); // temp3 = 2 * re * im
        }

        mpf_add(re, temp2, cre); // re = (re + im) * (re - im) + cre
        mpf_add(im, temp3, cim); // im = 2 * re * im + cim
    }
    else {
        if (use_threads) {

            #pragma omp parallel sections num_threads(3)
            {
                #pragma omp section
                {
                    mpf_mul(temp1, re, re); // re^2
                }
                #pragma omp section
                {
                    mpf_mul(temp2, im, im); // im^2
                }
                #pragma omp section
                {
                    mpf_add(temp3, re, im); // temp3 = re + im
                    mpf_mul(temp3, temp3, temp3); // temp3 = (re + im)^2
                }
            }
        }
        else {
            mpf_mul(temp1, re, re); // re^2
            mpf_mul(temp2, im, im); // im^2

            mpf_add(temp3, re, im); // temp3 = re + im
            mpf_mul(temp3, temp3, temp3); // temp3 = (re + im)^2
        }


        mpf_sub(re, temp1, temp2); // re = re^2 - im^2
        mpf_add(re, re, cre); // re = re^2 - im^2 + cre


        mpf_add(temp1, temp1, temp2); // temp1 = re^2 + im^2

        mpf_sub(im, temp3, temp1); // im = (re + im)^2 - re^2 - im^2
        mpf_add(im, im, cim); // im = (re + im)^2 - re^2 - im^2 + cim
    }

    *valRe = mpf_get_d(re);
    *valIm = mpf_get_d(im);

} 

void mpir_fz_square_plus_c_simple_with_reduction_deep(mpf_ptr re, mpf_ptr im, mpf_ptr temp1, mpf_ptr temp2, mpf_ptr temp3, mpf_srcptr cre, mpf_srcptr cim, int algorithm, int use_threads, double* mantissaRe, double* mantissaIm, long* expRe, long* expIm)
{

    if (algorithm == 0) {
        if (use_threads) {
            #pragma omp parallel sections num_threads(2)
            {
                #pragma omp section
                {
                    mpf_add(temp1, re, im); // temp1 = re + im
                    mpf_sub(temp2, re, im); // temp2 = re - im
                    mpf_mul(temp2, temp1, temp2); // temp2 = (re + im) * (re - im)
                }
                #pragma omp section
                {
                    mpf_mul(temp3, re, im); // temp3 = re * im
                    mpf_mul_2exp(temp3, temp3, 1); // temp3 = 2 * re * im
                }
            }
        }
        else {
            mpf_add(temp1, re, im); // temp1 = re + im
            mpf_sub(temp2, re, im); // temp2 = re - im
            mpf_mul(temp2, temp1, temp2); // temp2 = (re + im) * (re - im)

            mpf_mul(temp3, re, im); // temp3 = re * im
            mpf_mul_2exp(temp3, temp3, 1); // temp3 = 2 * re * im
        }

        mpf_add(re, temp2, cre); // re = (re + im) * (re - im) + cre
        mpf_add(im, temp3, cim); // im = 2 * re * im + cim
    }
    else {
        if (use_threads) {

            #pragma omp parallel sections num_threads(3)
            {
                #pragma omp section
                {
                    mpf_mul(temp1, re, re); // re^2
                }
                #pragma omp section
                {
                    mpf_mul(temp2, im, im); // im^2
                }
                #pragma omp section
                {
                    mpf_add(temp3, re, im); // temp3 = re + im
                    mpf_mul(temp3, temp3, temp3); // temp3 = (re + im)^2
                }
            }
        }
        else {
            mpf_mul(temp1, re, re); // re^2
            mpf_mul(temp2, im, im); // im^2

            mpf_add(temp3, re, im); // temp3 = re + im
            mpf_mul(temp3, temp3, temp3); // temp3 = (re + im)^2
        }


        mpf_sub(re, temp1, temp2); // re = re^2 - im^2
        mpf_add(re, re, cre); // re = re^2 - im^2 + cre


        mpf_add(temp1, temp1, temp2); // temp1 = re^2 + im^2

        mpf_sub(im, temp3, temp1); // im = (re + im)^2 - re^2 - im^2
        mpf_add(im, im, cim); // im = (re + im)^2 - re^2 - im^2 + cim
    }


    *mantissaRe = mpf_get_d_2exp(expRe, re);
    *mantissaIm = mpf_get_d_2exp(expIm, im);

}

void mpir_fz_norm_square_with_components(mpf_ptr re_sqr, mpf_ptr im_sqr, mpf_ptr norm_sqr, mpf_srcptr re, mpf_srcptr im, int use_threads)
{
    if (use_threads) {
        #pragma omp parallel sections num_threads(2)
        {
            #pragma omp section
            {
                mpf_mul(re_sqr, re, re); // re^2
            }
            #pragma omp section
            {
                mpf_mul(im_sqr, im, im); // im^2
            }
        }
    }
    else {
        mpf_mul(re_sqr, re, re); // re^2
        mpf_mul(im_sqr, im, im); // im^2
    }
    mpf_add(norm_sqr, re_sqr, im_sqr); // re^2 + im^2
}

void mpir_fz_norm_square(mpf_ptr norm_sqr, mpf_ptr temp1, mpf_srcptr re, mpf_srcptr im, int use_threads)
{
    if (use_threads) {
        #pragma omp parallel sections num_threads(2)
        {
            #pragma omp section
            {
                mpf_mul(norm_sqr, re, re); // re^2
            }
            #pragma omp section
            {
                mpf_mul(temp1, im, im); // im^2
            }
        }
    }
    else {
        mpf_mul(norm_sqr, re, re); // re^2
        mpf_mul(temp1, im, im); // im^2
    }
    mpf_add(norm_sqr, norm_sqr, temp1); // re^2 + im^2
}

void mpir_fz_get_d(double* valRe, double* valIm, mpf_srcptr re, mpf_srcptr im)
{
    *valRe = mpf_get_d(re);
    *valIm = mpf_get_d(im);
}

void mpir_fz_get_d_2exp(double* valRe, double* valIm, long* expRe, long* expIm, mpf_srcptr re, mpf_srcptr im)
{
    *valRe = mpf_get_d_2exp(expRe, re);
    *valIm = mpf_get_d_2exp(expIm, im);
}

void mpir_fz_set(mpf_ptr destre, mpf_ptr destim, mpf_srcptr srcre, mpf_srcptr srcim)
{
    mpf_set(destre, srcre);
    mpf_set(destim, srcim);
}


void mpir_fz_self_add(mpf_ptr re, mpf_ptr im, mpf_srcptr val_re, mpf_srcptr val_im)
{
    mpf_add(re, re, val_re);
    mpf_add(im, im, val_im);
}

void mpir_fz_self_sub(mpf_ptr re, mpf_ptr im, mpf_srcptr val_re, mpf_srcptr val_im)
{
    mpf_sub(re, re, val_re);
    mpf_sub(im, im, val_im);
}

void mpir_fz_rotation(mpf_ptr x, mpf_ptr y, mpf_ptr temp_re, mpf_ptr temp_im, mpf_ptr f, mpf_srcptr a, mpf_srcptr asb, mpf_srcptr apb)
{
    mpf_sub(f, x, y);
    mpf_mul(f, a, f);

    mpf_mul(temp_re, asb, y);
    mpf_mul(temp_im, apb, x);

    mpf_add(x, temp_re, f);
    mpf_sub(y, temp_im, f);
}

void mpir_fz_AsBmC(mpf_ptr temp, mpf_srcptr a, mpf_srcptr b, int c)
{
    mpf_mul_ui(temp, b, c);
    mpf_sub(temp, a, temp);
}

void mpir_fz_ApBmC(mpf_ptr temp, mpf_srcptr a, mpf_srcptr b, int c)
{
    mpf_mul_ui(temp, b, c);
    mpf_add(temp, a, temp);
}

void mpir_fz_ApBmC_DsEmG(mpf_ptr temp, mpf_ptr temp2, mpf_srcptr a, mpf_srcptr b, mpf_srcptr c, mpf_srcptr d, mpf_srcptr e, mpf_srcptr g)
{
    mpf_mul(temp, b, c);
    mpf_add(temp, a, temp);

    mpf_mul(temp2, e, g);
    mpf_sub(temp2, d, temp2);
}


void mpir_fz_ApBmC_DpEmG(mpf_ptr temp, mpf_ptr temp2, mpf_srcptr a, mpf_srcptr b, mpf_srcptr c, mpf_srcptr d, mpf_srcptr e, mpf_srcptr g)
{ 
    mpf_mul(temp, b, c);
    mpf_add(temp, a, temp);

    mpf_mul(temp2, e, g);
    mpf_add(temp2, d, temp2);
}

void mpir_fz_r_ball_pow2(mpf_ptr r, mpf_ptr az, mpf_srcptr r0, mpf_srcptr azsquare)
{
    mpf_add(az, r, az); // az = az + r
    mpf_mul(r, az, az); // r = (az + r)^2
    mpf_sub(r, r, azsquare);
    mpf_add(r, r, r0);
}