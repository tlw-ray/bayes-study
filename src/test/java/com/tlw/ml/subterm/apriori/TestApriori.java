package com.tlw.ml.subterm.apriori;

import org.apache.commons.collections.set.ListOrderedSet;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestApriori {
    @Test
    public void testApriori(){
        Set<Integer> set1 = createSet(new int[]{1, 3, 4});
        Set<Integer> set2 = createSet(new int[]{2, 3, 5});
        Set<Integer> set3 = createSet(new int[]{1, 2, 3, 5});
        Set<Integer> set4 = createSet(new int[]{2, 4});
        Set[] sets = new Set[]{set1, set2, set3, set4};
        Apriori apriori = new Apriori();
        apriori.input = sets;


    }

    public Set<Integer> createSet(int[] values){
        return new HashSet(Arrays.asList(values));
    }
}
