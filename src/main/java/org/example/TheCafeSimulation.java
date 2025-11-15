package org.example;

import com.sun.security.jgss.GSSUtil;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TheCafeSimulation {

    static BlockingQueue<String> orders = new ArrayBlockingQueue<>(1);
    

    public static void main(String[] args) throws InterruptedException {

        // 1. Barista thread - consumer
        Thread barista = new Thread(() -> {
            try {
                while(true) {
                    String order = orders.take();
                    System.out.println("Barista is preparing: "+order+ " on thread " +Thread.currentThread().getName());
                    Thread.sleep(2000);
                    System.out.println("Barista served: "+order+ " on thread " +Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "Barista");

        barista.start();

        // 4 customer threads (producers)
        createCustomer("Ajay", "Latte");
        createCustomer("Kavya", "Chai Latte");
        createCustomer("Akshra", "Smoothie");
        createCustomer("Eshanth", "Orieo Milkshake");
    }

    private static void createCustomer(String customerName, String order) {
        new Thread(() -> {
            try {
                System.out.println(customerName + " placing order: " +order+ " on thread " +Thread.currentThread().getName());
                orders.put(customerName + " - " +order);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, customerName).start();
    }
}
