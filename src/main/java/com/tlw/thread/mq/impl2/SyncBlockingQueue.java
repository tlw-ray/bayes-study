package com.tlw.thread.mq.impl2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author tlw
 */
public class SyncBlockingQueue<E> {

    private Queue<E> queue;
    private int capacity;

    public SyncBlockingQueue(){
        this(16);
    }

    public SyncBlockingQueue(int capacity){
        this.queue = new LinkedList();
        ArrayList a;
        this.capacity = capacity;
    }

    public synchronized void put(E elem) throws InterruptedException {
        while(queue.size() == capacity){
            this.wait();
        }
        if(queue.size() == 0){
            this.notifyAll();
        }
        queue.add(elem);
    }

    public synchronized E take() throws InterruptedException{
        while(queue.size() == 0){
            this.wait();
        }
        if(queue.size() == capacity){
            this.notifyAll();
        }
        return queue.remove();
    }
}
