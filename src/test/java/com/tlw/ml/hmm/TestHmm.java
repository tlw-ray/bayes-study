package com.tlw.ml.hmm;

import com.tlw.ml.hmm.e01.Model;
import org.junit.Test;

public class TestHmm {
    @Test
    public void test1(){
        Model model = new Model();
        model.saveModel("my.xml");
    }
}
