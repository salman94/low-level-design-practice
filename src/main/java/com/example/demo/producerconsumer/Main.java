package com.example.demo.producerconsumer;

public class Main {
    public static void main(String[] args) {
        ProducerConsumer producerConsumer = new ProducerConsumer(5, 100);
        Thread producerThread = new Thread(producerConsumer::produce);
        Thread consumerThread = new Thread(producerConsumer::consume);
        producerThread.start();
        consumerThread.start();
    }
}
