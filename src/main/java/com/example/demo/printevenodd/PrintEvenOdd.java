package com.example.demo.printevenodd;

class PrintEvenOddNumber {
    int counter = 1;
    int N = 50;

    public void printEvenNumber() {
        synchronized (this) {
            while (counter < N) {
                if(counter % 2 != 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                }
                System.out.println("Even number: "+ counter);
                counter++;
                notify();
            }
        }
    }
    public void printOddNumber() {
        synchronized (this) {
            while (counter < N) {
                if(counter % 2 == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {

                    }
                }
                System.out.println("odd number: "+ counter);
                counter++;
                notify();
            }
        }
    }
}

public class PrintEvenOdd {


    public static void main(String[] args) {
        PrintEvenOddNumber obj = new PrintEvenOddNumber();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                obj.printEvenNumber();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                obj.printOddNumber();
            }
        });

        t1.start();
        t2.start();
    }
}
