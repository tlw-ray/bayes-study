package com.tlw.spark;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MemoryCalc {

    public static void main(String[] args){
        Set<String> elements = new HashSet();
        for(int i=0;i<30;i++){
            elements.add(i + "");
        }
        System.out.println("---");
        MemoryCalc calc = new MemoryCalc();
        List<Set<String>> combinations = calc.getCombination(elements);
        for(Set<String> combination:combinations){
            System.out.println(combination);
        }
        System.out.println("size: " + combinations.size());
    }

    public List<Set<String>> getCombination(Set<String> elements){
        List<Set<String>> combinations = new ArrayList();
        for(String element:elements){

            List<Set<String>> newCombinations = new ArrayList();
            for(Set<String> combination:combinations){
                Set<String> newCombination = new HashSet(combination);
                newCombination.add(element);
                newCombinations.add(newCombination);
            }
            combinations.addAll(newCombinations);

            Set<String> elementCombination = new HashSet();
            elementCombination.add(element);
            combinations.add(elementCombination);


        }
        return combinations;
    }
}
