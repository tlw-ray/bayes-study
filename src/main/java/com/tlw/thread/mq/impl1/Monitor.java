package com.tlw.thread.mq.impl1;

import java.util.concurrent.BlockingQueue;

/**
 * @author tlw
 */
public class Monitor implements Runnable{

    BlockingQueue<String> blockingQueue;

    public Monitor(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Queue size: " + blockingQueue.size());
        }
    }

}
