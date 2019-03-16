package com.tlw.ml.hmm.e03;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

public class TestDice {
    @Test
    public void testDice6(){
        for(int i=3;i<9;i++){
            System.out.println("Dice" + i);
            testDice(i, 5000);
        }
    }

    //投dimension面的骰子rollCount次, 记录每种值出现的个数，并判定值的合理性
    private void testDice(int dimension, int rollCount){
        Dice dice = new Dice(dimension);
        Map<Integer, Integer> valueCountMap = new TreeMap();
        for(int i=0;i<rollCount;i++){
            int value = dice.roll();
            Integer count = valueCountMap.get(value);
            if(count == null){
                valueCountMap.put(value, 1);
            }else{
                valueCountMap.put(value, ++count);
            }
//            System.out.println("\tdice" + dimension + ".roll() => " + value);
            Assert.assertTrue(value > 0 && value < dimension + 1);
        }
        for(Map.Entry<Integer, Integer> entry:valueCountMap.entrySet()){
            System.out.println("\tdimension" + entry.getKey() + ": " + entry.getValue() + "次");
        }
    }
}
