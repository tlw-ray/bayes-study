package com.tlw.ml.subterm.apriori;

import org.apache.commons.collections4.set.ListOrderedSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 唐力伟
 * 本程序用于频繁集的挖掘
 * 首先用List<ListOrderedSet<Integer>>类型的record将矩阵形式的数据读入内存；
 *
 * 程序先求出k-1备选集，由备选集和数据库记录record求得满足支持度的k-1级集合，在满足支持度集合中求出满足置信度的集合，
 * 若满足置信度的集合为空，程序停止；
 * 否则输出满足置信度的集合，以及对应的支持度和置信度，并由满足支持度的k-1级集合求出k级备选集，进入下一轮循环；
 * 直至程序结束，输出全部频繁级
 */
public class Apriori {

    boolean endTag = false;
    // k-1频繁集的记数表
    Map<Integer, Integer> dCountMap = new HashMap();
    // k频繁集的记数表
    Map<Integer, Integer> dkCountMap = new HashMap();
    // 数据记录表
    List<ListOrderedSet<Integer>> record = new ArrayList();
    // 最小支持度
    final double MIN_SUPPORT = 0.2;
    // 最小置信度
    final double MIN_CONF = 0.8;
    // 置信度记录表
    List<Double> confCount = new ArrayList();
    // 满足支持度的集合
    List<ListOrderedSet<Integer>> confItemSet = new ArrayList();

    public Apriori(List<ListOrderedSet<Integer>> record){
        this.record = record;
        List<ListOrderedSet<Integer>> cItemSet = findFirstCandidate();// 获取第一次的备选集
        List<ListOrderedSet<Integer>> lItemSet = getSupportedItemSet(cItemSet);// 获取备选集cItemSet满足支持的集合

        while (endTag != true) {// 只要能继续挖掘
            List<ListOrderedSet<Integer>> ckItemSet = getNextCandidate(lItemSet);// 获取第下一次的备选集
            List<ListOrderedSet<Integer>> lkItemSet = getSupportedItemSet(ckItemSet);// 获取备选集cItemSet满足支持的集合
            getConfidencedItemSet(lkItemSet, lItemSet, dkCountMap, dCountMap);// 获取备选集cItemSet满足置信度的集合
            if (confItemSet.size() != 0) {// 满足置信度的集合不为空
                printConfItemSet(confItemSet);// 打印满足置信度的集合
            }
            confItemSet.clear();// 清空置信度的集合
            cItemSet = ckItemSet;// 保存数据，为下次循环迭代准备
            lItemSet = lkItemSet;
            dCountMap.clear();
            dCountMap.putAll(dkCountMap);
        }
    }

    /**
     * @param confItemSet2
     * 输出满足条件的频繁集
     */
    private void printConfItemSet(List<ListOrderedSet<Integer>> confItemSet2) {
        System.out.print("*********频繁模式挖掘结果***********\n");
        for (int i = 0; i < confItemSet2.size(); i++) {
            int j = 0;
            for (j = 0; j < confItemSet2.get(i).size() - 3; j++) {
                System.out.print(confItemSet2.get(i).get(j) + " ");
            }
            System.out.print("-->");
            System.out.print(confItemSet2.get(i).get(j++));
            System.out.print("相对支持度：" + confItemSet2.get(i).get(j++));
            System.out.print("置信度：" + confItemSet2.get(i).get(j++) + "\n");
        }

    }

    /**
     * @param lkItemSet
     * @param lItemSet
     * @param dkCountMap2
     * @param dCountMap2
     * 根据lkItemSet，lItemSet，dkCountMap2，dCountMap2求出满足置信度的集合
     */
    private List<ListOrderedSet<Integer>> getConfidencedItemSet(
            List<ListOrderedSet<Integer>> lkItemSet, List<ListOrderedSet<Integer>> lItemSet,
            Map<Integer, Integer> dkCountMap2, Map<Integer, Integer> dCountMap2) {
        for (int i = 0; i < lkItemSet.size(); i++) {
            getConfItem(lkItemSet.get(i), lItemSet, dkCountMap2.get(i),
                    dCountMap2);

        }
        return null;
    }

    /**
     * @param list
     * @param lItemSet
     * @param count
     * @param dCountMap2
     * 检验集合list是否满足最低置信度要求
     * 若满足则在全局变量confItemSet添加list
     * 如不满足则返回null
     */
    private List<Integer> getConfItem(ListOrderedSet<Integer> list,
                                            List<ListOrderedSet<Integer>> lItemSet, Integer count,
                                            Map<Integer, Integer> dCountMap2) {
        for (int i = 0; i < list.size(); i++) {
            ListOrderedSet<Integer> testList = new ListOrderedSet();
            for (int j = 0; j < list.size(); j++) {
                if (i != j) {
                    testList.add(list.get(j));
                }
            }
            int index = findConf(testList, lItemSet);//查找testList中的内容在lItemSet的位置
            Double conf = count * 1.0 / dCountMap2.get(index);
            if (conf > MIN_CONF) {//满足置信度要求
                testList.add(list.get(i));
                Double relativeSupport = count * 1.0 / (record.size() - 1);
                testList.add(relativeSupport);
                testList.add(conf);
                confItemSet.add(testList);//添加到满足置信度的集合中
            }
        }
        return null;
    }

    /**
     * @param testList
     * @param lItemSet
     * 查找testList中的内容在lItemSet的位置
     */
    private int findConf(ListOrderedSet<Integer> testList,
                                List<ListOrderedSet<Integer>> lItemSet) {
        for (int i = 0; i < lItemSet.size(); i++) {
            boolean notHaveTag = false;
            for (int j = 0; j < testList.size(); j++) {
                if (lItemSet.get(i).contains(testList.get(j))) {
                    notHaveTag = true;
                    break;
                }
            }
            if (notHaveTag == false) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param cItemSet
     * 求出cItemSet中满足最低支持度集合
     */
    private List<ListOrderedSet<Integer>> getSupportedItemSet(
            List<ListOrderedSet<Integer>> cItemSet) {
        boolean end = true;
        List<ListOrderedSet<Integer>> supportedItemSet = new ArrayList();
        int k = 0;
        for (int i = 0; i < cItemSet.size(); i++) {
            int count = countFrequent(cItemSet.get(i));//统计记录数
            if (count >= MIN_SUPPORT * (record.size() - 1)) {// count值大于支持度与记录数的乘积，即满足支持度要求
                if (cItemSet.get(0).size() == 1)
                    dCountMap.put(k++, count);
                else
                    dkCountMap.put(k++, count);
                supportedItemSet.add(cItemSet.get(i));
                end = false;
            }
        }
        endTag = end;
        return supportedItemSet;
    }

    /**
     * @param list
     * 统计数据库记录record中出现list中的集合的个数
     */
    private int countFrequent(ListOrderedSet<Integer> list) {
        int count = 0;
        for (int i = 1; i < record.size(); i++) {
            boolean notHavaThisList = false;
            for (int k = 0; k < list.size(); k++) {
                boolean thisRecordHave = false;
                for (int j = 1; j < record.get(i).size(); j++) {
                    if (list.get(k).equals(record.get(i).get(j)))
                        thisRecordHave = true;
                }
                if (!thisRecordHave) {// 扫描一遍记录表的一行，发现list.get(i)不在记录表的第j行中，即list不可能在j行中
                    notHavaThisList = true;
                    break;
                }
            }
            if (notHavaThisList == false)
                count++;
        }
        return count;
    }

    /**
     * @param cItemSet
     * @return nextItemSet
     * 根据cItemSet求出下一级的备选集合组，求出的备选集合组中的每个集合的元素的个数比cItemSet中的集合的元素大1
     */
    private List<ListOrderedSet<Integer>> getNextCandidate(
            List<ListOrderedSet<Integer>> cItemSet) {
        List<ListOrderedSet<Integer>> nextItemSet = new ArrayList();
        for (int i = 0; i < cItemSet.size(); i++) {
            ListOrderedSet<Integer> tempList = new ListOrderedSet();
            for (int k = 0; k < cItemSet.get(i).size(); k++) {
                tempList.add(cItemSet.get(i).get(k));
            }
            for (int h = i + 1; h < cItemSet.size(); h++) {
                for (int j = 0; j < cItemSet.get(h).size(); j++) {
                    tempList.add(cItemSet.get(h).get(j));
                    if (isSubsetInC(tempList, cItemSet)) {// tempList的子集全部在cItemSet中
                        ListOrderedSet<Integer> copyValueHelpList = new ListOrderedSet();
                        for (int p = 0; p < tempList.size(); p++)
                            copyValueHelpList.add(tempList.get(p));
                        if (isHave(copyValueHelpList, nextItemSet))//nextItemSet还没有copyValueHelpList这个集合
                            nextItemSet.add(copyValueHelpList);
                    }
                    tempList.remove(tempList.size() - 1);
                }
            }
        }

        return nextItemSet;
    }

    /**
     * @param copyValueHelpList
     * @param nextItemSet
     * @return boolean
     * 检验nextItemSet中是否包含copyValueHelpList
     */
    private boolean isHave(ListOrderedSet<Integer> copyValueHelpList,
                                  List<ListOrderedSet<Integer>> nextItemSet) {
        for (int i = 0; i < nextItemSet.size(); i++) {
            if (copyValueHelpList.equals(nextItemSet.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param tempList
     * @param cItemSet
     * @return
     *检验 tempList是不是cItemSet的子集
     */
    private boolean isSubsetInC(ListOrderedSet<Integer> tempList,
                                       List<ListOrderedSet<Integer>> cItemSet) {
        boolean haveTag = false;
        for (int i = 0; i < tempList.size(); i++) {// k集合tempList的子集是否都在k-1级频繁级中
            ListOrderedSet<Integer> testList = new ListOrderedSet();
            for (int j = 0; j < tempList.size(); j++) {
                if (i != j) {
                    testList.add(tempList.get(j));
                }
            }
            for (int k = 0; k < cItemSet.size(); k++) {
                if (testList.equals(cItemSet.get(k))) {// 子集存在于k-1频繁集中
                    haveTag = true;
                    break;
                }
            }
            if (haveTag == false) {// 其中一个子集不在k-1频繁集中
                return false;
            }
        }

        return haveTag;
    }

    /**
     *根据数据库记录求出第一级备选集
     */
    private List<ListOrderedSet<Integer>> findFirstCandidate() {
        List<ListOrderedSet<Integer>> tableList = new ArrayList();
        ListOrderedSet<Integer> lineList = new ListOrderedSet();

        int size = 0;
        for (int i = 1; i < record.size(); i++) {
            for (int j = 1; j < record.get(i).size(); j++) {
                if (lineList.isEmpty()) {
                    lineList.add(record.get(i).get(j));
                } else {
                    boolean haveThisItem = false;
                    size = lineList.size();
                    for (int k = 0; k < size; k++) {
                        if (lineList.get(k).equals(record.get(i).get(j))) {
                            haveThisItem = true;
                            break;
                        }
                    }
                    if (haveThisItem == false) {
                        lineList.add(record.get(i).get(j));
                    }
                }
            }
        }
        for (int i = 0; i < lineList.size(); i++) {
            ListOrderedSet<Integer> helpList = new ListOrderedSet();
            helpList.add(lineList.get(i));
            tableList.add(helpList);
        }
        return tableList;
    }
}
