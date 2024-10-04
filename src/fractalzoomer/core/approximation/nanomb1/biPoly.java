package fractalzoomer.core.approximation.nanomb1;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public class biPoly {
    int m_m, m_n;
    MantExpComplex[][] tab;
    MantExpComplex[][] ttab;
    void mcopy(){
        for(int l=0; l <= m_m; l++) {
            for (int c = 0; c <= m_n; c++) {
                ttab[l][c] = MantExpComplex.copy(tab[l][c]);
            }
        }
    }

    MantExpComplex csqrc(int k, int l){
        MantExpComplex v = MantExpComplex.create();
        for(int i=0; i <= k; i++) {
            for (int j = 0; j <= l; j++) {
                v.plus_mutable(ttab[i][j].times(ttab[k - i][l - j]));
                v.Normalize();
            }
        }
        return v;
    }

    public biPoly(int m, int n) {
        m_m = m;
        m_n = n;
        tab = new MantExpComplex[m_m + 1][m_n + 1];
        ttab = new MantExpComplex[m_m + 1][m_n + 1];
        for(int l=0; l <= m_m; l++) {
            for (int c = 0; c <= m_n; c++) {
                tab[l][c] = MantExpComplex.create();
            }
        }
        tab[1][0] = MantExpComplex.create(MantExp.ONE, MantExp.ZERO);
    }

    void sqr() {
        mcopy();
        for (int i = 0; i <= m_m; i++) {
            for (int j = 0; j <= m_n; j++) {
                tab[i][j] = csqrc(i, j);
            }
        }
    }

    public void cstep(MantExpComplex z){
        sqr();
        tab[0][0] = z;
        tab[0][1].plus_mutable(MantExpComplex.create(MantExp.ONE, MantExp.ZERO));
    }

    /*MantExpComplex eval(MantExpComplex u, MantExpComplex v){
        MantExpComplex r = new MantExpComplex();
        MantExpComplex ui = new MantExpComplex(new MantExpComplex(MantExp.ONE, MantExp.ZERO));
        MantExpComplex usquare = u.square();
        for(int i=0; i <= m_m; i+=2){
            MantExpComplex vj = new MantExpComplex(ui);
            for(int j=0; j <= m_n; j++){
                r.plus_mutable(tab[i][j].times(vj));
                r.Reduce();
                vj.times_mutable(v);
                vj.Reduce();
            }
            ui.times_mutable(usquare);
            ui.Reduce();
        }
        return r;
    }

    MantExpComplex eval_dc(MantExpComplex u, MantExpComplex v){
        MantExpComplex r = new MantExpComplex();
        MantExpComplex ui = new MantExpComplex(1, 0);
        MantExpComplex usquare = u.square();
        for(int i=0; i <= m_m; i+=2){
            MantExpComplex vj = new MantExpComplex(ui);
            for(int j=1; j <= m_n; j++){
                r.plus_mutable(tab[i][j].times(new MantExp(j)).times_mutable(vj));
                r.Reduce();
                vj.times_mutable(v);
                vj.Reduce();
            }
            ui.times_mutable(usquare);
            ui.Reduce();
        }
        return r;
    }

    MantExpComplex eval_dz(MantExpComplex u, MantExpComplex v){
        MantExpComplex r = new MantExpComplex();
        MantExpComplex ui = new MantExpComplex(u);
        MantExpComplex usquare = u.square();
        for(int i=2; i <= m_m; i+=2){
            MantExpComplex vj = new MantExpComplex(i, 0).times(ui);
            for(int j=0; j <= m_n; j++){
                r.plus_mutable(tab[i][j].times(vj));
                r.Reduce();
                vj.times_mutable(v);
                vj.Reduce();
            }
            ui.times_mutable(usquare);
            ui.Reduce();
        }
        return r;
    }*/

    public static final int max_iters = 10;
    public MantExp getRadius(){
        //return tab[0][1].norm().divide_mutable(tab[0][2].norm());
        //return tab[0][1].divide(tab[0][2]).norm();
        MantExp r = new MantExp();
        for(int i = 0; i < max_iters; i++){
            MantExpComplex den = MantExpComplex.create();
            MantExp rr = new MantExp(MantExp.ONE);
            for(int j = 2; j <=m_n; j++){
                den.plus_mutable(tab[0][j].times(rr));
                den.Normalize();
                rr.multiply_mutable(r);
                rr.Normalize();
            }
            //r = tab[0][1].norm().divide_mutable(den.norm());
            r = tab[0][1].divide(den).norm();
            r.Normalize();
        }
        r.divide2_mutable();
        return r;
    }

    public void print(){
        for(int i=0; i <= m_m; i++){
            for(int j=0; j <= m_n; j++){
                System.out.println("i: " + i + "\tj: " + j + "\tval: " + tab[i][j]);
            }
            System.out.println("-----------------------");
        }
    }
}
