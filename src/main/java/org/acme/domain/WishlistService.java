package org.acme.domain;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class WishlistService {
    private Map<String, Wishlist> wishlists = new HashMap<>();

    public void addToWishlist(String customerId, UUID eventId) {
        Wishlist wishlist = wishlists.getOrDefault(customerId, new Wishlist(customerId));
        wishlist.addEvent(eventId);
        wishlists.put(customerId, wishlist);
    }

    public void removeFromWishlist(String customerId, UUID eventId) {
        Wishlist wishlist = wishlists.get(customerId);
        if (wishlist != null) {
            wishlist.removeEvent(eventId);
        }
    }

    public Wishlist getWishlist(String customerId) {
        return wishlists.getOrDefault(customerId, new Wishlist(customerId));
    }

    public List<UUID> getAllEventsInWishlist(String customerId) {
        Wishlist wishlist = wishlists.get(customerId);
        if (wishlist != null) {
            return wishlist.getEvents();
        }
        return List.of();
    }
}
