package com.tlw.thread.mq.impl1;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{

    private BlockingQueue<String> blockingQueue;

    public Consumer(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while(true){
            try{
                Thread.sleep(3000);
                //消费物品
                System.out.println("[消费者线程" + Thread.currentThread() + "] 消费物品: " + blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
