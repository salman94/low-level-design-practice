package com.example.demo.producerconsumer;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {
    private Queue<Integer> q;
    private int capacity;
    private int totalItemToBeProduced;
    private int currentItem;

    public ProducerConsumer(int capacity, int totalItemToBeProduced) {
        this.q = new LinkedList<Integer>();
        this.capacity = capacity;
        this.totalItemToBeProduced = totalItemToBeProduced;
        this.currentItem = 1;
    }

    public int getTotalItemToBeProduced() {
        return this.totalItemToBeProduced;
    }

    public synchronized void produce() {
        while(totalItemToBeProduced > 0) {
            System.out.println("Producing items...");
            while (q.size() < this.capacity && totalItemToBeProduced > 0) {
                q.offer(currentItem++);
                totalItemToBeProduced--;
            }
            System.out.println("Items production completed!");
            notifyAll();
            try {
                wait();
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public synchronized void consume() {
        while (totalItemToBeProduced > 0) {
            if(q.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            while (!q.isEmpty()) {
                System.out.print(q.poll()+", ");
            }
            System.out.println();
            notifyAll();
        }

    }
}
