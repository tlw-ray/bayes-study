package com.tlw.ml.sub.apriori;

import org.apache.commons.collections4.set.ListOrderedSet;
import org.apache.commons.math3.util.Combinations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author 唐力伟
 * 本程序用于频繁集的挖掘
 * 首先用List<ListOrderedSet<Integer>>类型的record将矩阵形式的数据读入内存；
 *
 * 程序先求出k-1备选集，由备选集和数据库记录record求得满足支持度的k-1级集合，在满足支持度集合中求出满足置信度的集合，
 * 若满足置信度的集合为空，程序停止；
 * 否则输出满足置信度的集合，以及对应的支持度和置信度，并由满足支持度的k-1级集合求出k级备选集，进入下一轮循环；
 * 直至程序结束，output 输出每次过滤频繁子项的过程
 *
 * 参考: https://www.cnblogs.com/pinard/p/6293298.html
 */
public class Apriori {

    ListOrderedSet<ListOrderedSet<Integer>> input;
    ListOrderedSet<Map<ListOrderedSet<Integer>, Integer>> output = new ListOrderedSet();

    // 以下三个断言(支持度、置信度、提升度)有一个满足就会决定该项集将被移除
    Predicate<Map.Entry<ListOrderedSet<Integer>, Integer>> supportPredicate = entry -> entry.getValue() < 1;
    Predicate<Map.Entry<ListOrderedSet<Integer>, Integer>> confidencePredicate;
    Predicate<Map.Entry<ListOrderedSet<Integer>, Integer>> liftPredicate;

    int level = 1;

    public void process(){
        ListOrderedSet<Integer> allElements = getAllElements();
        Map<ListOrderedSet<Integer>, Integer> elementsCountMap = countItemSet(allElements, level++);
        output.add(elementsCountMap);
        do{
            allElements = getAllElements(elementsCountMap);
            Map<ListOrderedSet<Integer>, Integer> newElementsCountMap = countItemSet(allElements, level++);
            if(newElementsCountMap.size() > 0){
                output.add(newElementsCountMap);
            }else{
                break;
            }
        }while(level < allElements.size());
        printOutput();
    }

    // 首次取得所有元素的集合
    private ListOrderedSet<Integer> getAllElements(){
        Set<Integer> elements = input.stream().flatMap(e -> e.stream()).collect(Collectors.toSet());
        return ListOrderedSet.listOrderedSet(elements);
    }

    // 取得所有元素的集合
    private ListOrderedSet<Integer> getAllElements(Map<ListOrderedSet<Integer>, Integer> countMap){
        ListOrderedSet<Integer> result = new ListOrderedSet();
        for(ListOrderedSet<Integer> keySet:countMap.keySet()){
            result.addAll(keySet);
        }
        return result;
    }

    //为元素组合计数
    private Map<ListOrderedSet<Integer>, Integer> countItemSet(ListOrderedSet<Integer> elements, int setSize){
        Map<ListOrderedSet<Integer>, Integer> elementSetCountMap = new HashMap();
        Combinations combinations = new Combinations(elements.size(), setSize);
        combinations.iterator().forEachRemaining(e -> {
            // 计算元素组被输入包含的次数
            int count = input.stream().map(i -> containAll(i, e)?1:0).reduce(0, Integer::sum);
            ListOrderedSet<Integer> combination = new ListOrderedSet();
            for(Integer v:e){
                combination.add(v);
            }
            elementSetCountMap.put(combination, count);
        });

        // 移除不符合条件的
        Set<ListOrderedSet<Integer>> removeSet = new HashSet();
        for(Map.Entry<ListOrderedSet<Integer>, Integer> entry:elementSetCountMap.entrySet()){
            if(predicateToRemove(entry)){
                removeSet.add(entry.getKey());
            }
        }
        elementSetCountMap.keySet().removeAll(removeSet);
        return elementSetCountMap;
    }

    protected boolean predicateToRemove(Map.Entry<ListOrderedSet<Integer>, Integer> entry){
        // 默认不移除
        boolean support = false;
        boolean confidence = false;
        boolean lift = false;
        if(supportPredicate != null){
            support = supportPredicate.test(entry);
        }
        if(confidencePredicate != null){
            confidence = confidencePredicate.test(entry);
        }
        if(liftPredicate != null){
            lift = liftPredicate.test(entry);
        }
        return support || confidence || lift;
    }

    private boolean containAll(ListOrderedSet<Integer> leftElements, int[] rightElements){
        for(int value: rightElements){
            if(!leftElements.contains(value)){
                return false;
            }
        }
        return true;
    }

    public void printOutput(){
        for(Map<ListOrderedSet<Integer>, Integer> map:output){
            System.out.println("--------");
            for(Map.Entry<ListOrderedSet<Integer>, Integer> elementGroups: map.entrySet()){
                System.out.println("\t" + elementGroups.getKey() + "\t" + elementGroups.getValue());
            }
        }
    }
}
