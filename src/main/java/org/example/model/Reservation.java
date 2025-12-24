package org.example.model;

public class Reservation {

    private String passengerName;
    private Seat assignedSeat;

    public Reservation(String passengerName, Seat assignedSeat) {
        this.passengerName = passengerName;
        this.assignedSeat = assignedSeat;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public Seat getAssignedSeat() {
        return assignedSeat;
    }

    public void setAssignedSeat(Seat assignedSeat) {
        this.assignedSeat = assignedSeat;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "passengerName='" + passengerName + '\'' +
                ", assignedSeat=" + assignedSeat +
                '}';
    }
}
