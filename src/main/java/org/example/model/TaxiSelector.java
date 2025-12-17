package org.example.model;

import java.util.Collection;

public interface TaxiSelector {
    Taxi choose(Collection<Taxi> taxis, RiderRequest req);
}
