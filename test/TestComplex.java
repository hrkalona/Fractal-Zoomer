package test;
import fractalzoomer.core.Complex;

/*
 * Copyright (C) 2020 hrkalona
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 * @author hrkalona
 */
public class TestComplex {
    
    public static void main(String[] args) {
        
        Complex a = new Complex(3.345325, -0.4831);
        Complex b = new Complex(-1.2324, -13.5677);
        
        double re = -20.4343235;
        double im = 231.43;
        
        double c1 = -54;
        double c2 = 2;
        double c3 = 10.33;
        int s = 3;
        
        assert new Complex(1, 1).compare(new Complex(1, 1)) == 0;
        assert new Complex(1, 1).compare(new Complex(3, 1)) != 0;
        assert new Complex().compare(new Complex(1, 0)) == 1;
        assert new Complex().compare(new Complex(-1, 0)) == -1;
        
        assert a.plus(b).compare(new Complex(a).plus_mutable(b)) == 0 && a.plus(b).compare(new Complex(2.1129249999999997, -14.0508)) == 0;
        
        assert a.plus(re).compare(new Complex(a).plus_mutable(re)) == 0 && a.plus(re).compare(new Complex(-17.088998500000002, -0.4831)) == 0;
        assert a.plus_i(im).compare(new Complex(a).plus_i_mutable(im)) == 0 && a.plus_i(im).compare(new Complex(3.345325, 230.9469)) == 0;
        
        assert a.sub(b).compare(new Complex(a).sub_mutable(b)) == 0 && a.sub(b).compare(new Complex(4.577725, 13.0846)) == 0;
        assert a.sub(re).compare(new Complex(a).sub_mutable(re)) == 0 && a.sub(re).compare(new Complex(23.7796485, -0.4831)) == 0;
        assert a.sub_i(im).compare(new Complex(a).sub_i_mutable(im)) == 0 && a.sub_i(im).compare(new Complex(3.345325, -231.91310000000001)) == 0;
        assert a.r_sub(re).compare(new Complex(a).r_sub_mutable(re)) == 0 && a.r_sub(re).compare(new Complex(-23.7796485, 0.4831)) == 0;
        assert a.i_sub(im).compare(new Complex(a).i_sub_mutable(im)) == 0 && a.i_sub(im).compare(new Complex(-3.345325, 231.91310000000001)) == 0;
        
        assert a.times(b).compare(new Complex(a).times_mutable(b)) == 0 && a.times(b).compare(new Complex(-10.6773344, -44.7929935625)) == 0;
        assert a.times(re).compare(new Complex(a).times_mutable(re)) == 0 && a.times(re).compare(new Complex(-68.3594532626375, 9.87182168285)) == 0;
        assert a.times_i(im).compare(new Complex(a).times_i_mutable(im)) == 0 && a.times_i(im).compare(new Complex(111.803833, 774.20856475)) == 0;
        
        assert a.divide(b).compare(new Complex(a).divide_mutable(b)) == 0 && a.divide(b).compare(new Complex(0.013102157318186852, 0.24775548535705633)) == 0;
        assert a.divide(re).compare(new Complex(a).divide_mutable(re)) == 0 && a.divide(re).compare(new Complex(-0.16371107171715274, 0.023641594986004794)) == 0;
        assert a.divide_i(im).compare(new Complex(a).divide_i_mutable(im)) == 0 && a.divide_i(im).compare(new Complex(-0.002024996678061702, -0.01446403861318874)) == 0;
        assert a.r_divide(re).compare(new Complex(a).r_divide_mutable(re)) == 0 && a.r_divide(re).compare(new Complex(-5.983539311784339, -0.8640858037778135)) == 0;
        assert a.i_divide(im).compare(new Complex(a).i_divide(im)) == 0 && a.i_divide(im).compare(new Complex(-9.786248982908553, 67.76688755692105)) == 0;
        
        
        assert b.remainder(a).compare(new Complex(b).remainder_mutable(a)) == 0 && b.remainder(a).compare(new Complex(0.7, -0.1864000000000008)) == 0;
        assert b.remainder(re).compare(new Complex(b).remainder_mutable(re)) == 0 && b.remainder(re).compare(new Complex(-1.2324, 6.866623500000001)) == 0;
        assert b.remainder_i(im).compare(new Complex(b).remainder_i_mutable(im)) == 0 && b.remainder_i(im).compare(new Complex(-1.2324, -13.5677)) == 0;
        assert b.r_remainder(re).compare(new Complex(b).r_remainder_mutable(re)) == 0 && b.r_remainder(re).compare(new Complex(-6.866623500000001, -1.2324)) == 0;
        assert b.i_remainder(im).compare(new Complex(b).i_remainder_mutable(im)) == 0 && b.i_remainder(im).compare(new Complex(6.184600000000003, -1.685699999999997)) == 0;
        assert new Complex(28.7, 0).remainder(new Complex(10.33, 0)).compare(new Complex(28.7, 0).remainder(10.33)) == 0 && new Complex(28.7, 0).remainder(10.33).compare(new Complex(10.33, 0).r_remainder(28.7)) == 0 && new Complex(28.7, 0).remainder(new Complex(10.33, 0)).compare(new Complex(8.04, 0)) == 0;
        assert new Complex(10, 0).remainder(new Complex(0, 4)).compare(new Complex(2, 0)) == 0;
        assert new Complex(-28.7, 0).remainder(new Complex(10.33, 0)).compare(new Complex(-28.7, 0).remainder(10.33)) == 0 && new Complex(-28.7, 0).remainder(10.33).compare(new Complex(10.33, 0).r_remainder(-28.7)) == 0 && new Complex(-28.7, 0).remainder(new Complex(10.33, 0)).compare(new Complex(2.2900000000000027, 0.0)) == 0;
        assert new Complex(28.7, 0).remainder(new Complex(-10.33, 0)).compare(new Complex(28.7, 0).remainder(-10.33)) == 0 && new Complex(28.7, 0).remainder(-10.33).compare(new Complex(-10.33, 0).r_remainder(28.7)) == 0 && new Complex(28.7, 0).remainder(new Complex(-10.33, 0)).compare(new Complex(-2.2900000000000027, 0.0)) == 0;
                
        assert a.reciprocal().compare(new Complex(a).reciprocal_mutable()) == 0 && a.reciprocal().compare(new Complex(0.29281807698622064, 0.04228600001256775)) == 0;
        
        assert a.square().compare(new Complex(a).square_mutable()) == 0 && a.square().compare(new Complex(10.957813745625, -3.2322530149999995)) == 0;
        assert a.cube().compare(new Complex(a).cube_mutable()) == 0 && a.cube().compare(new Complex(35.09594683703645, -16.10665663791631)) == 0;
        assert a.fourth().compare(new Complex(a).fourth_mutable()) == 0 && a.fourth().compare(new Complex(109.62622253083158, -70.83685303420968)) == 0;
        assert a.fifth().compare(new Complex(a).fifth_mutable()) == 0 && a.fifth().compare(new Complex(332.51405918712743, -289.9327234813123)) == 0;
        assert a.sixth().compare(new Complex(a).sixth_mutable()) == 0 && a.sixth().compare(new Complex(972.3010963363552, -1130.556730173422)) == 0;       
        assert a.seventh().compare(new Complex(a).seventh_mutable()) == 0 && a.seventh().compare(new Complex(2706.4912087546372, -4251.798353007495)) == 0;       
        assert a.eighth().compare(new Complex(a).eighth_mutable()) == 0 && a.eighth().compare(new Complex(7000.048918589185, -15531.153228224164)) == 0;       
        assert a.ninth().compare(new Complex(a).ninth_mutable()) == 0 && a.ninth().compare(new Complex(15914.33852402427, -55338.478805779436)) == 0;       
        assert a.tenth().compare(new Complex(a).tenth_mutable()) == 0 && a.tenth().compare(new Complex(26504.61541180944, -192813.41355190022)) == 0;       
        
        assert a.norm() == 3.3800273616680974;
        assert a.norm_squared() == 11.424584965624998;
        assert a.nnorm(b).compare(new Complex(1.2126783840275477, 0.10585083710215108)) == 0;
        assert a.nnorm(re) == 0.4831;
        assert a.distance(b) == 13.862262561920582;
        assert a.distance_squared(b) == 192.162323335625;
        assert a.distance(re) == 23.784555249017213;
        assert a.distance_squared(re) == 565.7050683935523;
        assert a.arg() == -0.14341899607641226;
        
        assert a.getAbsRe() == 3.345325;
        assert a.getAbsIm() == 0.4831;
        
        assert a.abs().compare(new Complex(a).abs_mutable()) == 0 && a.abs().compare(new Complex(3.345325, 0.4831)) == 0;
        assert a.absre().compare(new Complex(a).absre_mutable()) == 0 && a.absre().compare(new Complex(3.345325, -0.4831)) == 0;
        assert b.absim().compare(new Complex(b).absim_mutable()) == 0 && b.absim().compare(new Complex(-1.2324, 13.5677)) == 0;
        
        assert a.conjugate().compare(new Complex(a).conjugate_mutable()) == 0 && a.conjugate().compare(new Complex(3.345325, 0.4831)) == 0;
        assert a.negative().compare(new Complex(a).negative_mutable()) == 0 && a.negative().compare(new Complex(-3.345325, 0.4831)) == 0;
        
        assert a.pow(re).compare(new Complex(a).pow_mutable(re)) == 0 && a.pow(re).compare(new Complex(-1.5210384728260604E-11, 3.2566503686652785E-12)) == 0;
        
        assert a.pow(b).compare(new Complex(-0.025560803730433852, 0.018998153232222585)) == 0;
        assert a.log().compare(new Complex(a).log_mutable()) == 0 && a.log().compare(new Complex(1.2178838046302378, -0.14341899607641226)) == 0;
        
        assert a.cos().compare(new Complex(-1.09583760422851, -0.10159024263978415)) == 0;
        assert a.cosh().compare(new Complex(12.577172881934427, -6.581075581547162)) == 0;
        assert a.acos().compare(new Complex(0.15002831809201034, 1.8894155030298765)) == 0;
        assert a.acosh().compare(new Complex(1.8894155030298765, -0.1500283180920105)) == 0;
        
        
        assert a.sin().compare(new Complex(-0.22639863460186987, 0.49172738299921986)) == 0;
        assert a.sinh().compare(new Complex(12.54595802233261, -6.5974495682878525)) == 0;
        assert a.asin().compare(new Complex(1.4207680087028862, -1.8894155030298765)) == 0;
        assert a.asinh().compare(new Complex(1.9314382470211322, -0.13759299755553026)) == 0;
        
        
        assert a.tan().compare(new Complex(0.16359362750426965, -0.46388889863803007)) == 0;
        assert a.tanh().compare(new Complex(0.9985863914085921, -0.0020415598930307102)) == 0;
        assert a.atan().compare(new Complex(1.2854966318766878, -0.038961251964596745)) == 0;
        assert a.atanh().compare(new Complex(0.3010209084434252, -1.5245859752485194)) == 0;
        
        
        assert a.cot().compare(new Complex(0.6761302580232247, 1.917246567089264)) == 0;
        assert a.coth().compare(new Complex(1.0014114240344418, 0.0020473355308273586)) == 0;
        assert a.acot().compare(new Complex(0.2852996949182087, 0.038961251964596606)) == 0;
        assert a.acoth().compare(new Complex(0.30102090844342516, 0.046210351546377246)) == 0;
        
        
        assert a.sec().compare(new Complex(-0.9047681039953747, 0.08387703694685479)) == 0;
        assert a.sech().compare(new Complex(0.062419022084538374, 0.032661100067619325)) == 0;
        assert a.asec().compare(new Complex(1.273922519338159, -0.04420599222034658)) == 0;
        assert a.asech().compare(new Complex(0.044205992220346574, 1.2739225193381591)) == 0;
        
        
        assert a.csc().compare(new Complex(-0.7725540527495531, -1.677951738763894)) == 0;
        assert a.csch().compare(new Complex(0.062440253561870496, 0.032834991410959254)) == 0;
        assert a.acsc().compare(new Complex(0.2968738074567375, 0.04420599222034658)) == 0;
        assert a.acsch().compare(new Complex(0.2890189076092181, 0.040590479480386556)) == 0;
        
        
        assert a.vsin().compare(new Complex(2.0958376042285103, 0.10159024263978415)) == 0;
        assert a.avsin().compare(new Complex(2.9188537748181185, -1.5242453623166143)) == 0;
        assert a.vcos().compare(new Complex(-0.09583760422851006, -0.10159024263978415)) == 0;
        assert a.avcos().compare(new Complex(0.1137095869672411, 2.1553929440040482)) == 0;
        
        assert a.cvsin().compare(new Complex(1.2263986346018698, -0.49172738299921986)) == 0;
        assert a.acvsin().compare(new Complex(-1.348057448023222, 1.5242453623166143)) == 0;
        assert a.cvcos().compare(new Complex(0.7736013653981302, 0.49172738299921986)) == 0;
        assert a.acvcos().compare(new Complex(1.4570867398276555, -2.1553929440040482)) == 0;
        
        assert a.hvsin().compare(new Complex(1.0479188021142551, 0.05079512131989208)) == 0;
        assert a.ahvsin().compare(new Complex(2.9708787405767394, -2.4390316924070246)) == 0;
        assert a.hvcos().compare(new Complex(-0.04791880211425503, -0.05079512131989208)) == 0;
        assert a.ahvcos().compare(new Complex(0.17071391301305372, 2.4390316924070246)) == 0;
        
        assert a.hcvsin().compare(new Complex(0.6131993173009349, -0.24586369149960993)) == 0;
        assert a.ahcvsin().compare(new Complex(-1.4000824137818444, 2.4390316924070334)) == 0;
        assert a.hcvcos().compare(new Complex(0.3868006826990651, 0.24586369149960993)) == 0;
        assert a.ahcvcos().compare(new Complex(1.4447761224344187, -2.736927887409852)) == 0;
        
        assert a.exsec().compare(new Complex(-1.9047681039953748, 0.08387703694685479)) == 0;
        assert a.aexsec().compare(new Complex(1.34154747492061, -0.02594909552132161)) == 0;
        assert a.excsc().compare(new Complex(-1.7725540527495531, -1.677951738763894)) == 0;
        assert a.aexcsc().compare(new Complex(0.22924885187428656, 0.02594909552132161)) == 0;
        
        assert a.exp().compare(new Complex(25.123130904267036, -13.178525149835014)) == 0;
        assert a.sqrt().compare(new Complex(a).sqrt_mutable()) == 0 && a.sqrt().compare(new Complex(1.8337601208538834, -0.13172388103168214)) == 0;
        assert a.gaussian_integer().compare(new Complex(a).gaussian_integer_mutable()) == 0 && a.gaussian_integer().compare(new Complex(3.0, 0.0)) == 0;
        
        assert a.gamma_la().compare(new Complex(2.358246720044937, -1.3186055640614383)) == 0;
        assert b.gamma_la().compare(new Complex(1.4983004090127188E-11, -2.028961842272722E-12)) == 0;
        assert a.gamma_st().compare(new Complex(2.2970341743994145, -1.2949373145312)) == 0;
        assert a.factorial().compare(new Complex(7.25208336073623, -5.550433149047519)) == 0;
        
        assert a.floor().compare(new Complex(a).floor_mutable()) == 0 && a.floor().compare(new Complex(3.0, -1.0)) == 0;
        assert a.ceil().compare(new Complex(a).ceil_mutable()) == 0 && a.ceil().compare(new Complex(4.0, 0.0)) == 0;
        assert a.round().compare(new Complex(a).round_mutable()) == 0 && a.round().compare(new Complex(3.0, 0.0)) == 0;
        assert a.trunc().compare(new Complex(a).trunc_mutable()) == 0 && a.trunc().compare(new Complex(3.0, 0.0)) == 0;
        assert a.flip().compare(new Complex(a).flip_mutable()) == 0 && a.flip().compare(new Complex(-0.4831, 3.345325)) == 0;
        
        assert a.toBiPolar(b).compare(new Complex(-1.013342173280222, 3.365513126890851)) == 0;
        assert a.fromBiPolar(b).compare(new Complex(2.6557870031317807, 0.024689867880533598)) == 0;
        
        assert a.erf().compare(new Complex(1.0000027283411026, 6.207741045043023E-7)) == 0;
        assert a.riemann_zeta().compare(new Complex(1.1295457552066417, 0.06089539928635845)) == 0;
        assert b.riemann_zeta().compare(new Complex(-1.6622512094095478, 2.36553771585889)) == 0;
        assert a.dirichlet_eta().compare(new Complex(0.9235547666001223, -0.023469831368777172)) == 0;
        
        assert a.inflection(b).compare(new Complex(-151.483590984375, 106.22770107)) == 0;
        assert a.fold_out(b).compare(new Complex(3.345325, -0.4831)) == 0;
        assert a.fold_in(b).compare(new Complex(0.29281807698622064, -0.04228600001256775)) == 0;
        assert a.fold_right(b).compare(new Complex(3.345325, -0.4831)) == 0;
        assert a.fold_left(b).compare(new Complex(-5.810125, -0.4831)) == 0;
        assert a.fold_up(b).compare(new Complex(3.345325, -0.4831)) == 0;
        assert a.fold_down(b).compare(new Complex(3.345325, -26.6523)) == 0;

        assert a.pinch(b, c1, c2, c3).compare(new Complex(19.899815016756442, 73.94577830831507)) == 0;
        assert a.shear(b).compare(new Complex(3.9406974399999997, -45.8714660025)) == 0;
        assert a.kaleidoscope(b, c1, c2, c3, s).compare(new Complex(3.161287476050073, -19.072594453164225)) == 0;
        assert a.twirl(b, c1, c2).compare(new Complex(3.345325, -0.4831)) == 0;
        assert a.circle_inversion(b, c2).compare(new Complex(-1.1371112894861355, -13.29533443378759)) == 0;
        
        assert a.rotate(b).compare(new Complex(a).rotate_mutable(b)) == 0 && a.rotate(b).compare(new Complex(4.225011728599037, -0.7032122764649219)) == 0;
        
        assert a.ripples(b, a, 0).compare(new Complex(4.623362497928302, -0.36518753515508745)) == 0;
        assert a.ripples(b, a, 1).compare(new Complex(4.656690228416099, -0.8470842687043493)) == 0;
        assert a.ripples(b, a, 2).compare(new Complex(5.968055456832197, -0.7213314625913013)) == 0;

        assert new Complex(a).square_mutable_plus_c_mutable(b).compare(new Complex(9.725413745625, -16.799953015)) == 0;

        assert a.fibonacci().compare(new Complex(2.2789968632650943,-0.12051629744072523)) == 0;

        System.out.println("Test Completed.");
     
    }
    
}
