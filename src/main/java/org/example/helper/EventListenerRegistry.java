package org.example.helper;

import org.example.model.Event;
import org.example.model.EventListener;

import java.util.concurrent.CopyOnWriteArrayList;

public class EventListenerRegistry {
    private static final CopyOnWriteArrayList<EventListener> listeners =
            new CopyOnWriteArrayList<>();

    public static void subscribe(EventListener eventListener) {
        listeners.add(eventListener);
    }

    public static void unSubscribe(EventListener eventListener) {
        listeners.remove(eventListener);
    }

    static void notify(Event e) {
        for(EventListener listener: listeners) {
            listener.onEvent(e);
        }
    }
}
