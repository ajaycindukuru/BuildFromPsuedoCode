package org.example.helper;

import org.example.model.RiderRequest;
import org.example.model.Taxi;
import org.example.model.TaxiSelector;

import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaxiService implements TaxiSelector {

    @Override
    public Taxi choose(Collection<Taxi> taxis, RiderRequest riderRequest) {
        return taxis.stream()
                .filter(t -> t.isAvailabilityStatus().get())
                .sorted(Comparator.comparingDouble(t-> t.getLocation().distanceTo(riderRequest.from())))
                .filter(taxi -> taxi.isAvailabilityStatus().compareAndSet(true, false))
                .findFirst().orElseThrow(() -> new IllegalStateException("No available taxis"));
    }
}
