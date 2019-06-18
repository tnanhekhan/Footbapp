package com.example.footbapp.model;

import java.util.List;

public class EventResource {
    private List<Event> events;

    public EventResource(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
