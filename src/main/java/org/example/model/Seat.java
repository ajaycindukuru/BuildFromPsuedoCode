package org.example.model;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Seat {
    private String seatNumber;
    private boolean assigned;
    private AtomicInteger attempts = new AtomicInteger(0);
    public final ReentrantLock lock = new ReentrantLock(true);

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
        this.assigned = false;
    }

    public Seat(String seatNumber, boolean assigned) {
        this.seatNumber = seatNumber;
        this.assigned = assigned;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public AtomicInteger getAttempts() {
        return attempts;
    }

    public boolean getAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatNumber='" + seatNumber + '\'' +
                "attempts=" + attempts +
                '}';
    }
}
