package org.acme.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wishlist {
    private String customerId;
    private List<UUID> events;

    public Wishlist(String customerId) {
        this.customerId = customerId;
        this.events = new ArrayList<>();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<UUID> getEvents() {
        return events;
    }

    public void setEvents(List<UUID> events) {
        this.events = events;
    }

    public void addEvent(UUID event) {
        events.add(event);
    }

    public void removeEvent(UUID event) {
        events.remove(event);
    }
}
