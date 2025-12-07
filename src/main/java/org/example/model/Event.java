package org.example.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Event {
    private int eventId;
    private String eventName;
    private LocalDateTime eventDate;
    private final List<String> subscribers = new CopyOnWriteArrayList<>();

    public Event(int eventId, String eventName, LocalDateTime eventDate) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public void subscribe(String user) {
        subscribers.add(user);
    }

    public List<String> getSubscribers() {
        return subscribers;
    }
}
