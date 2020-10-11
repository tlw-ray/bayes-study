package com.tlw.ml.subterm.apriori;

import org.apache.commons.collections4.set.ListOrderedSet;
import org.apache.commons.math3.util.Combinations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    ListOrderedSet<ListOrderedSet<Integer>> input;
    ListOrderedSet<Map<ListOrderedSet<Integer>, Integer>> output = new ListOrderedSet();

    int level = 1;

    public void process(){
        ListOrderedSet<Integer> allTerms = getAllTerms();
        Map<ListOrderedSet<Integer>, Integer> termsCountMap = countTermSet(allTerms, level++);
        output.add(termsCountMap);
        do{
            allTerms = getAllTerm(termsCountMap);
            Map<ListOrderedSet<Integer>, Integer> newTermsCountMap = countTermSet(allTerms, level++);
            if(newTermsCountMap.size() > 0){
                output.add(newTermsCountMap);
            }else{
                break;
            }
        }while(level < allTerms.size());
        printOutput();
    }

    // 首次取得所有因此的集合
    private ListOrderedSet<Integer> getAllTerms(){
        Set<Integer> terms = input.stream().flatMap(e -> e.stream()).collect(Collectors.toSet());
        return ListOrderedSet.listOrderedSet(terms);
    }

    // 取得所有因子的集合
    private ListOrderedSet<Integer> getAllTerm(Map<ListOrderedSet<Integer>, Integer> countMap){
        ListOrderedSet<Integer> result = new ListOrderedSet();
        for(ListOrderedSet<Integer> keySet:countMap.keySet()){
            result.addAll(keySet);
        }
        return result;
    }

    //为因子组合计数
    private Map<ListOrderedSet<Integer>, Integer> countTermSet(ListOrderedSet<Integer> terms, int setSize){
        Map<ListOrderedSet<Integer>, Integer> termSetCountMap = new HashMap();
        Combinations combinations = new Combinations(terms.size(), setSize);
        combinations.iterator().forEachRemaining(e -> {
            int count = input.stream().map(i -> containAll(i, e)?1:0).reduce(0, Integer::sum);
            ListOrderedSet<Integer> combination = new ListOrderedSet();
            for(Integer v:e){
                combination.add(v);
            }
            termSetCountMap.put(combination, count);
        });

        // 移除出现不足一次的
        Set<ListOrderedSet<Integer>> removeSet = new HashSet();
        for(Map.Entry<ListOrderedSet<Integer>, Integer> entry:termSetCountMap.entrySet()){
            if(entry.getValue() < 2){
                removeSet.add(entry.getKey());
            }
        }
        termSetCountMap.keySet().removeAll(removeSet);
        return termSetCountMap;
    }

    private boolean containAll(ListOrderedSet<Integer> items, int[] terms){
        for(int value: terms){
            if(!items.contains(value)){
                return false;
            }
        }
        return true;
    }

    public void printOutput(){
        for(Map<ListOrderedSet<Integer>, Integer> map:output){
            System.out.println("--------");
            for(Map.Entry<ListOrderedSet<Integer>, Integer> termGroups: map.entrySet()){
                System.out.println("\t" + termGroups.getKey() + "\t" + termGroups.getValue());
            }
        }
    }
}
