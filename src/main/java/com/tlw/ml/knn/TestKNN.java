package com.tlw.ml.knn;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * KNN算法测试类
 * @author Rowen
 * @qq 443773264
 * @mail luowen3405@163.com
 * @blog blog.csdn.net/luowen3405
 * @data 2011.03.25
 */
public class TestKNN {

    /**
     * 从数据文件中读取数据
     * @param datas 存储数据的集合对象
     * @param inputStream 数据文件的路径
     */
    public void read(List<List<Double>> datas, InputStream inputStream){
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String data = br.readLine();
            List<Double> l = null;
            while (data != null) {
                String t[] = data.split("    ");
                l = new ArrayList<Double>();

                for (int i = 0; i < t.length; i++) {

                    l.add(Double.parseDouble(t[i]));
//                    System.out.println(l);
                }
                datas.add(l);
                data = br.readLine();

            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 程序执行入口
     * @param args
     */
    public static void main(String[] args) {
        TestKNN t = new TestKNN();
        InputStream dataInputStream = TestKNN.class.getResourceAsStream("datafile");
        InputStream testInputStream = TestKNN.class.getResourceAsStream("testfile");

        try {
            List<List<Double>> datas = new ArrayList();
            List<List<Double>> testDatas = new ArrayList();
            t.read(datas, dataInputStream);
            t.read(testDatas, testInputStream);
//            System.out.println(datas);
            KNN knn = new KNN();
            for (int i = 0; i < testDatas.size(); i++) {
                List<Double> test = testDatas.get(i);
                System.out.print("测试元组: ");
                for (int j = 0; j < test.size(); j++) {
                    System.out.print(test.get(j) + " ");
                }
                System.out.print("类别为: ");
                System.out.println(Math.round(Float.parseFloat((knn.knn(datas, test, 3)))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
