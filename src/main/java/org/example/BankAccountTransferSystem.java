package org.example;

import org.example.model.Account;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccountTransferSystem {


    public static void main(String[] args) {
        var customerOne = new Account(1, "Ajay Indukuru", 100000.00);
        var customerTwo = new Account(2, "Kavya Pemmu", 150000.00);
        var customerThree = new Account(3, "Akshra Indukuru", 250000.00);
        var customerFour = new Account(4, "Eshanth Indukuru", 250000.00);

        new Thread(() -> transfer(customerOne, customerTwo, 1500.00), "T1").start();
        new Thread(() -> transfer(customerOne, customerTwo, 15000.00), "T2").start();
        new Thread(() -> transfer(customerThree, customerFour, 10000.00), "T3").start();
        new Thread(() -> transfer(customerFour, customerThree, 20000.00), "T4").start();
    }



    static void transfer(Account from, Account to, Double transferAmount) {
        var first = from.getAccountNumber() < to.getAccountNumber() ? from : to;
        var second = from.getAccountNumber() < to.getAccountNumber() ? to : from;

        first.getLock().lock();
        second.getLock().lock();
        try {
            // check if from account has enough balance
            if (from.getBalance() < transferAmount)
                throw new InsufficientBalance(from.getCustomerName() + "doesn't have enough balance");

            from.setBalance(from.getBalance() - transferAmount);
            to.setBalance(to.getBalance() + transferAmount);

            System.out.println(Thread.currentThread().getName() +
                    " transferred " + transferAmount + " from " +
                    from.getCustomerName() + " to " + to.getCustomerName());
        } finally {
            second.getLock().unlock();
            first.getLock().unlock();
        }
    }

    static class InsufficientBalance extends RuntimeException {
        InsufficientBalance(String message) {
            super(message);
        }
    }
}
