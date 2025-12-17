package org.example.model;

import java.util.concurrent.atomic.AtomicBoolean;

public class Taxi {
    int taxiId;
    Location location;
    AtomicBoolean availabilityStatus;

    public Taxi(int taxiId, Location location, AtomicBoolean availabilityStatus) {
        this.taxiId = taxiId;
        this.location = location;
        this.availabilityStatus = availabilityStatus;
    }

    public int getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(int taxiId) {
        this.taxiId = taxiId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public AtomicBoolean isAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AtomicBoolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}
