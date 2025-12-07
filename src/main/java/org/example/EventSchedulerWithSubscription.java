package org.example;

import org.example.model.Event;

import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.concurrent.*;

public class EventSchedulerWithSubscription {

    static final TreeMap<LocalDateTime, Event> events = new TreeMap<>();

    public static void main(String[] args) {
        var event1 = new Event(1, "Minni's Birthday", LocalDateTime.of(2025, 11, 29, 19, 39));
        var event2 = new Event(2, "Ajay's Birthday", LocalDateTime.of(2025, 11, 29, 19, 40));
        var event3 = new Event(3, "Akshra's Birthday", LocalDateTime.of(2025, 11, 29, 19, 41));
        var event4 = new Event(4, "Eshanth's Birthday", LocalDateTime.of(2025, 11, 29, 19, 38));

        events.put(event1.getEventDate(), event1);
        events.put(event2.getEventDate(), event2);
        events.put(event3.getEventDate(), event3);
        events.put(event4.getEventDate(), event4);

        EventListenerRegistry.subscribe(e -> {
            for (String subscriber: e.getSubscribers()) {
                System.out.println("Hi " +subscriber+ "!. This is a reminder for your event: " +e.getEventName() + " will start at " +e.getEventDate());
            }
        });

        event1.subscribe("User1");
        event1.subscribe("User3");
        event2.subscribe("User1");
        event2.subscribe("User2");
        event2.subscribe("User5");
        event3.subscribe("User5");
        event4.subscribe("User1");
        event4.subscribe("User5");

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        ScheduledFuture<?> handler = scheduler.scheduleAtFixedRate(EventSchedulerWithSubscription::sendNotifications, 0, 1, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            handler.cancel(true);
            scheduler.shutdown();
            System.out.println("Event handler stopped");
        }, 10, TimeUnit.MINUTES);
    }

    private static void sendNotifications() {
        if (events.isEmpty()) return;

        var now = LocalDateTime.now();
        System.out.println(now);
        var first = events.firstEntry();
        if (first==null) return;
        var eventTime = first.getKey();
        var notifyAt = eventTime.minusMinutes(1);
        if (now.isAfter(notifyAt) && eventTime.isBefore(now)) {
            Event e = events.pollFirstEntry().getValue();
            EventListenerRegistry.notify(e);
      }
    }
}
