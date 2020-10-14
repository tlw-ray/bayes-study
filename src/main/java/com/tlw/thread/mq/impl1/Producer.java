package com.tlw.thread.mq.impl1;

import java.util.concurrent.BlockingQueue;

/**
 * @author tlw
 */
public class Producer implements Runnable{

    private BlockingQueue<String> blockingQueue;

    public Producer(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        int index = 0;
        while(true){
            try{
                Thread.sleep(1000);
                index++;
                String item = "Item(" + Thread.currentThread().getName() + ", " + index + ")";
                blockingQueue.put(item);
                System.out.println("生产者线程[" + Thread.currentThread().getName() + "] 生产物品: " + item);
            }catch(InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }
}
