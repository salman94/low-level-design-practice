package com.example.demo.producerconsumer;

import java.beans.IntrospectionException;
import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {
    private Queue<Integer> q;
    private int capacity;

    public ProducerConsumer(int capacity) {
        this.q = new LinkedList<>();
        this.capacity = capacity;
    }

    public synchronized void produce(int item) throws InterruptedException {
        while (q.size() == capacity) {
            System.out.println("Buffer capacity is full, can't produce anymore items");
            wait();
        }
        q.offer(item);
        System.out.println();
        notifyAll();
    }

    public synchronized void consume() throws InterruptedException{
        while (q.isEmpty()) {
            System.out.println("Buffer is empty, we have to wait until there are items in the buffer");
            wait();
        }
        System.out.println("Consuming: "+q.poll());
        notifyAll();
    }
}
