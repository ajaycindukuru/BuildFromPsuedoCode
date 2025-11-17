package org.example.model;

import java.time.Duration;

public enum TrafficSignals {
    GREEN(1, 2000),
    YELLOW(2, 500),
    RED(3, 2000);

    private final int duration;
    private final int order;

    TrafficSignals(int order, int duration){
        this.order = order;
        this.duration = duration;
    }

    public int getSignalDuration() {
        return this.duration;
    }

    public int getSignalOrder() {
        return this.order;
    }
}
