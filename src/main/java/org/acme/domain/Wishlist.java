package org.acme.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wishlist {
    private UUID customerId;
    private List<UUID> events;

    public Wishlist(UUID customerId) {
        this.customerId = customerId;
        this.events = new ArrayList<>();
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
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
