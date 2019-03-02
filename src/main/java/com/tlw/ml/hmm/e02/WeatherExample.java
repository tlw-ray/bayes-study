package com.tlw.ml.hmm.e02;

import static com.tlw.ml.hmm.e02.WeatherExample.Weather.*;
import static com.tlw.ml.hmm.e02.WeatherExample.Activity.*;

public class WeatherExample
{
    enum Weather
    {
        Rainy,
        Sunny,
    }
    enum Activity
    {
        walk,
        shop,
        clean,
    }
    static int[] states = new int[]{Rainy.ordinal(), Sunny.ordinal()};
    static int[] observations = new int[]{walk.ordinal(), shop.ordinal(), clean.ordinal()};
    static double[] start_probability = new double[]{0.6, 0.4};
    static double[][] transition_probability = new double[][]{
            {0.7, 0.3},
            {0.4, 0.6},
    };
    static double[][] emission_probability = new double[][]{
            {0.1, 0.4, 0.5},
            {0.6, 0.3, 0.1},
    };

    public static void main(String[] args)
    {
        int[] result = Viterbi.compute(observations, states, start_probability, transition_probability, emission_probability);
        for (int r : result)
        {
            System.out.print(Weather.values()[r] + " ");
        }
        System.out.println();
    }
}
