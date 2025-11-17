package org.example;

import org.example.model.Directions;
import org.example.model.TrafficSignals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Set;

import static org.example.model.Directions.*;

public class TrafficLightController {

    public static void main(String[] args) throws InterruptedException {
        var directionsSet = Set.of(EnumSet.of(NORTH, SOUTH), EnumSet.of(EAST, WEST));
        while(true) {
            for (EnumSet<Directions> direction: directionsSet){
                trafficSignal(direction);
            }
        }
    }

    private static void trafficSignal(EnumSet<Directions> direction) throws InterruptedException {
        var traffic =
            new Thread(() -> {
                try {
                    var trafficSignals = Arrays.stream(TrafficSignals.values())
                            .sorted(Comparator.comparing(TrafficSignals::getSignalOrder))
                            .toList();
                    for (TrafficSignals signal : trafficSignals) {
                        Thread.sleep(signal.getSignalDuration());
                        System.out.println("Traffic signaled :: " + signal + " for " + signal.getSignalDuration() / 1000.00 + " seconds in " + direction + " directions.");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

        traffic.start();
        traffic.join();
    }
}
