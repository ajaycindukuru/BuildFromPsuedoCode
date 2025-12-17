package org.example;

import org.example.helper.TaxiService;
import org.example.model.Location;
import org.example.model.RiderRequest;
import org.example.model.Taxi;
import org.example.model.TaxiSelector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;


public class TaxiDispatchSystem {

    private static final RiderRequest POISON_PILL =  new RiderRequest("poison", new Location(-1, -1), new Location(-1, -1));
    static ConcurrentHashMap<Integer, Taxi> taxis = new ConcurrentHashMap<>();
    static BlockingQueue<RiderRequest> rideRequests = new LinkedBlockingQueue<>();
    static TaxiSelector taxiSelector = new TaxiService();
    static List<String> assignments = Collections.synchronizedList(new ArrayList<>());

    static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException {
        taxis.put(1, new Taxi(1, new Location(12.2, 121.12), new AtomicBoolean(true)));
        taxis.put(2, new Taxi(2, new Location(32.2, 645.8), new AtomicBoolean(true)));
        taxis.put(3, new Taxi(3, new Location(3.34, 645.8), new AtomicBoolean(true)));
        taxis.put(4, new Taxi(4, new Location(546.23, 534.23), new AtomicBoolean(true)));
        taxis.put(5, new Taxi(5, new Location(137.26, 158.58), new AtomicBoolean(true)));

        for (int i=1; i<=10; i++) {
            executorService.submit(() -> {
                while (true) {
                    try {
                        var req = rideRequests.take();
                        if (req == POISON_PILL) break;
                        var assignedTaxi = taxiSelector.choose(taxis.values(), req);
                        assignments.add("Rider " + req.name() + " is assigned taxi " + assignedTaxi.getTaxiId());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        producer();

        //Poison Pill
        for (int i=1; i<=10; i++) {
            rideRequests.put(POISON_PILL);
        }

        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.SECONDS);

        assignments.forEach(System.out::println);
    }

    public static void producer() throws InterruptedException {
        rideRequests.put(new RiderRequest("rider1", new Location(22.6, 44.36), new Location(55.36, 66.21)));
        rideRequests.put(new RiderRequest("rider2", new Location(476.5, 34.3), new Location(546.3, 345.23)));
        rideRequests.put(new RiderRequest("rider3", new Location(56.2, 989.3), new Location(23.3, 684.2)));
        rideRequests.put(new RiderRequest("rider4", new Location(879.2, 54.2), new Location(324.25, 564.3)));
        rideRequests.put(new RiderRequest("rider5", new Location(345.3, 546.3), new Location(234.3, 678.3)));
        rideRequests.put(new RiderRequest("rider6", new Location(980.2, 34.3), new Location(675.2, 52.2)));
        rideRequests.put(new RiderRequest("rider7", new Location(56.7, 54.2), new Location(67.3, 345.3)));
        rideRequests.put(new RiderRequest("rider8", new Location(234.32, 356.3), new Location(234.3, 2345.3)));
        rideRequests.put(new RiderRequest("rider9", new Location(123.3, 12.3), new Location(345.3, 33.2)));
        rideRequests.put(new RiderRequest("rider10", new Location(545.2, 234.2), new Location(53.3, 23.3)));
    }
}
