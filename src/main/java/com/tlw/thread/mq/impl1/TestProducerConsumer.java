package com.tlw.thread.mq.impl1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author tlw
 */
public class TestProducerConsumer {
    public static void main(String[] args){
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue(20);

        for(int i=0; i<4; i++){
            new Thread(new Producer(blockingQueue)).start();
            new Thread(new Consumer(blockingQueue)).start();
        }
        new Thread(new Monitor(blockingQueue)).start();
    }
}
