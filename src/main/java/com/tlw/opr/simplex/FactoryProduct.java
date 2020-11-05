package com.tlw.opr.simplex;

/**
 * 某工厂要安排生产Ⅰ、Ⅱ两种产品，已知生产单位产品所需的设备台时及A、B两种原材料的消耗，
 * 如表所示，表中右边一列是每日设备能力及原材料供应的限量，
 * 该工厂生产一单位产品Ⅰ可获利2元，
 * 生产一单位产品Ⅱ可获利3元，
 * 问应如何安排生产，使其获利最多？
 *
 * 生产:     A产品     B产品      资源限量
 * 设备:   1台时/件   2台时/件      8台时
 * 原料A:   4kg/件       0        16千克
 * 原料B:     0元     4kg/件      12千克
 * 利润:      2元       3元
 * 产量:      X1件      X2件
 *
 * 决策变量: X1, X2
 * 目标函数: max Z = 2*X1 + 3*X2
 * 约束条件:
 *      X1 + 2*X2 <= 8
 *      4*X1 <= 16
 *      4*X2 <= 12
 *      X1 >= 0
 *      X2 >= 0
 */
public class FactoryProduct {
    public static void main(String[] args){
//        q0();
//        q1();
        q11();
//        q2();
    }

    static void q0(){
        double[][] A = {
                { 1, 2, 1, 0, 0 },
                { 2, 1, 0, 1, 0 },
                { 4, 3, 0, 0, 1 } };
        double[] price = { 3, 2, 0, 0, 0 };
        double[] source = { 5, 4 ,9};
        Simplex simplex = new Simplex(A, price, source);
        simplex.calc();
    }

    static void q1(){
        double[][] A = new double[][]{
                {1, 2, 1, 0, 0},
                {4, 0, 0, 1, 0},
                {0, 4, 0, 0, 1},
        };
        double[] price = new double[]{2, 3, 0, 0, 0};
        double[] source = new double[]{8, 16, 12};
        Simplex simplex = new Simplex(A, price, source);
        simplex.calc();
    }

    static void q11(){
        double[][] A = new double[][]{
                {1, 2, 1, 0},
                {4, 0, 0, 1},
        };
        double[] price = new double[]{2, 3, 0, 0};
        double[] source = new double[]{8, 16};
        Simplex simplex = new Simplex(A, price, source);
        simplex.calc();
    }

    static void q2(){
        double[][] A = new double[][]{
                {2, 1, 1, 0},
                {1, 2, 0, 1},
        };
        double[] source = new double[]{12, 9};
        double[] price = new double[]{1, 1, 0, 0};
        Simplex simplex = new Simplex(A, price, source);
        simplex.calc();
    }
}
