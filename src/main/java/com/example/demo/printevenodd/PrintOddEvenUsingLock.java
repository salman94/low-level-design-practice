package com.example.demo.printevenodd;

import com.fasterxml.jackson.databind.ObjectMapper;

class PrintOddEvenUsingLockNumber implements Runnable{
    static int counter = 1;
    int MAX = 50;
    static Object lock = new Object();

    int remainder;
    PrintOddEvenUsingLockNumber(int remainder) {
        this.remainder = remainder;
    }

    @Override
    public void run() {
        while (counter < MAX) {
            synchronized (lock) {
                while (counter % 2 != remainder) { // wait for numbers other than remainder
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " " + counter);
                counter++;
                lock.notifyAll();
            }
        }
    }
}

public class PrintOddEvenUsingLock {

    public static void main(String[] args) {
        PrintOddEvenUsingLockNumber oddNumber = new PrintOddEvenUsingLockNumber(1);
        PrintOddEvenUsingLockNumber evenNumber = new PrintOddEvenUsingLockNumber(0);

        Thread t1 = new Thread(oddNumber, "Odd");
        Thread t2 = new Thread(evenNumber, "Even");
        t1.start();
        t2.start();

    }
}
