package com.tlw.ml.knn;
/**
 * KNN结点类，用来存储最近邻的k个元组相关的信息
 * @author Rowen
 * @qq 443773264
 * @mail luowen3405@163.com
 * @blog blog.csdn.net/luowen3405
 * @data 2011.03.25
 */
public class KNNNode {
    // 元组标号
    private int index;
    // 与测试元组的距离
    private double distance;
    // 所属类别
    private String c;
    public KNNNode(int index, double distance, String c) {
        super();
        this.index = index;
        this.distance = distance;
        this.c = c;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public String getC() {
        return c;
    }
    public void setC(String c) {
        this.c = c;
    }
}
