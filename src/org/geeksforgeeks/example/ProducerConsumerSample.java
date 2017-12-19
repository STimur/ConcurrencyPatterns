package org.geeksforgeeks.example;

import java.util.LinkedList;

public class ProducerConsumerSample {
    public static void main(String[] args)
            throws InterruptedException {
        final PC pc = new PC();

        Thread t1 = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public static class PC {
        LinkedList<Integer> list = new LinkedList<>();
        final int CAPACITY = 5;

        public void produce() throws InterruptedException {
            int value = 0;
            while (true) {
                synchronized (this) {
                    while (list.size() == CAPACITY)
                        wait();
                    System.out.println("Producer produced-" + value);
                    list.add(value++);
                    notify();
                    Thread.sleep(1000);
                }
                randomRest();
            }
        }

        public void consume() throws InterruptedException {
            while (true) {
                synchronized (this) {
                    while (list.size() == 0)
                        wait();
                    int value = list.removeFirst();
                    System.out.println("Consumer consumed-" + value);
                    notify();
                    Thread.sleep(1000);
                }
                randomRest();
            }
        }

        private void randomRest() throws InterruptedException {
            if (Math.random() < 0.5)
                Thread.sleep(0, 1);
        }
    }
}