package com.tlw.ml.sub.apriori;

import org.apache.commons.collections4.set.ListOrderedSet;
import org.apache.commons.math3.util.Combinations;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TestApriori {
    @Test
    public void testApriori(){
        ListOrderedSet<Integer> set1 = createSet(new Integer[]{0, 2, 3});
        ListOrderedSet<Integer> set2 = createSet(new Integer[]{1, 2, 4});
        ListOrderedSet<Integer> set3 = createSet(new Integer[]{0, 1, 2, 4});
        ListOrderedSet<Integer> set4 = createSet(new Integer[]{1, 4});
        ListOrderedSet<ListOrderedSet<Integer>> input = new ListOrderedSet();
        input.add(set1);
        input.add(set2);
        input.add(set3);
        input.add(set4);
        Apriori apriori = new Apriori();
        apriori.input = input;
        apriori.process();


    }

    public ListOrderedSet<Integer> createSet(Integer[] values){
        Set<Integer> treeSet = new HashSet();
        treeSet.addAll(Arrays.asList(values));
        return ListOrderedSet.listOrderedSet(treeSet);
    }

    @Test
    public void testCombination(){
        Combinations combinations = new Combinations(3, 1);
        combinations.iterator().forEachRemaining(e -> System.out.println(Arrays.toString(e)));
    }

    @Test
    public void testMapReduce(){
        int[] ints = new int[]{1,2,3,4};
        int count = Arrays.stream(ints).map(i -> i % 2).reduce(0, Integer::sum);
        System.out.println(count);
    }

    @Test
    public void testMapReduce2(){
        int[] ints1 = new int[]{1,2,3,4};
        int[] ints2 = new int[]{1,2,5};
        int[][] ints = new int[][]{ints1, ints2};
        int count = Arrays.stream(ints).flatMapToInt(x -> Arrays.stream(x)).boxed().collect(Collectors.toSet()).size();
        System.out.println(count);
    }
}
